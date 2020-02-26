package hr.fer.opp.webmeisters.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import hr.fer.opp.webmeisters.data.form.FilterForm;
import hr.fer.opp.webmeisters.data.model.*;
import hr.fer.opp.webmeisters.data.view.EventView;
import hr.fer.opp.webmeisters.data.view.ReviewView;
import hr.fer.opp.webmeisters.service.*;
import hr.fer.opp.webmeisters.validator.EventFormValidator;
import hr.fer.opp.webmeisters.validator.ReviewFormValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import hr.fer.opp.webmeisters.data.form.EventForm;
import hr.fer.opp.webmeisters.data.form.ReviewForm;

import org.springframework.web.servlet.ModelAndView;

//vazna napomena -> mora get context path a ne get real path zbog deploymenta
@Controller
public class EventController {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private ReviewFormValidator reviewFormValidator;

	@Autowired
	ServletContext servletContext;

	@Autowired
	private EventFormValidator eventValidator;

	@Autowired
	private EventService eventService;

	@Autowired
	private OrganiserService organiserService;

	@Autowired
	private VisitorService visitorService;

	@Autowired
	private PictureService pictureService;

	@Autowired
	private VideoService videoService;

	@Autowired
	private InterestTypeService interestTypeService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private InterestService interestService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private NotificationService notificationService;

	private static final int TIME_IN_MILLIS_BEFORE_CLOSING_REVIEWS = 5 * 60 * 1000;
	public static final long HOUR = 3600*1000; // in milli-seconds.

	@RequestMapping(value = "/addEvent/{id}", method = RequestMethod.GET)
	public String addEventGetMethod(@PathVariable String id, Model model) {

		EventForm eventForm = new EventForm();
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("areas", areaService.getAllAreas());

		model.addAttribute("id", id);
		model.addAttribute("eventForm", eventForm);
		return "/addEvent";
	}

	@RequestMapping(value = "/addEvent/{id}", method = RequestMethod.POST)
	public ModelAndView addEventPostMethod(@PathVariable String id, @ModelAttribute("eventForm") EventForm eventForm,
			BindingResult result, Model model, @RequestParam("files") MultipartFile[] files,
			@SessionAttribute("loggedInUser") User user) {
		eventValidator.validate(eventForm, result);

		if (result.hasErrors()) {
			model.addAttribute("id", id);
			model.addAttribute("categories", categoryService.getAllCategories());
			model.addAttribute("areas", areaService.getAllAreas());
			return new ModelAndView("addEvent", "eventForm", eventForm);
		}
		StringBuilder fileNames = new StringBuilder();

		List<Picture> slike = new ArrayList<>();
		List<Video> video = new ArrayList<>();

		for (MultipartFile file : files) {

			if (file.getContentType().startsWith("image")) {
				
				
				Path filepath = Paths.get(servletContext.getRealPath(servletContext.getContextPath()),"/WEB-INF/pictures/", file.getOriginalFilename());
				System.out.println("ONAKO slika:"+filepath.toString());
				Picture slika = new Picture();
				slika.setName("id:" + user.getId() + "-" + file.getOriginalFilename());
				pictureService.saveNewPicture(slika);

				slike.add(slika);
				try {
					Files.write(filepath, file.getBytes());
				} catch (IOException e) {
				}
			} else if (file.getContentType().startsWith("video")) {
				Path filepath = Paths.get(servletContext.getRealPath(servletContext.getContextPath()),"/resources/static/videos/", file.getOriginalFilename());
				System.out.println("ONAKO video:"+filepath.toString());
				Video vid = new Video();
				vid.setName("id:" + user.getId() + "-" + file.getOriginalFilename());
				videoService.saveNewVideo(vid);
				video.add(vid);
				try {
					Files.write(filepath, file.getBytes());
					
				} catch (IOException e) {
					// System.out.println("GRESKA VIDEO SE NIJE SPREMIO!");
				}
			} else {
				// TO DO
			}

		}
		eventForm.setPictures(slike);
		eventForm.setVideos(video);

		Event e = null;
		if (!result.hasErrors()) {
			Organiser org = organiserService.findById(user.getId());
			e = eventService.createEvent(eventForm, org);
		}
		model.addAttribute("msg", "Successfully uploaded files " + fileNames.toString());

		// ovo neki thread napravit
		List<Notification> notifications = notificationService.getAll();
		List<Notification> toSend = new ArrayList<>();

		for (Notification n : notifications) {
			if (n.getAreas().contains(e.getArea()) && n.getCategories().contains(e.getCategory())) {
				toSend.add(n);
			}
		}
		if (toSend.isEmpty()) {
			return new ModelAndView("redirect:/showEvents/" + id);
		}
		

		String[] mail = new String[toSend.size()];
		for (int i = 0; i < toSend.size(); i++) {
			mail[i] = toSend.get(i).getVisitor().getEmail();
		}

		//sendEmail(e.getCategory().getName(), e.getArea().getName(), e.getOrganiser().getFullName(), mail);
		SendMailJob sendMailJob =new SendMailJob(e.getCategory().getName(), e.getArea().getName(), e.getOrganiser().getFullName(), mail,javaMailSender);
		
		Thread thread = new Thread(sendMailJob, "worker");
		thread.start();
		return new ModelAndView("redirect:/showEvents/" + id);

	}

	@RequestMapping(value = "/showEvents/{id}", method = RequestMethod.POST)
	public String showEventsPostMethod(@PathVariable String id, Model model,
			@ModelAttribute("filter") FilterForm filter, @ModelAttribute("sort") String sort) {

		model.addAttribute("filter", filter);
		model.addAttribute("sort", sort);
		Organiser org = organiserService.findById(Integer.parseInt(id));
		model.addAttribute("organiser", org);

		Set<Event> eventi = org.getEvents();

		filtriraj(eventi, filter, model, filter.getSort());

		return "/showEvents";
	}

	private void prebrojiInterese(Event e, EventView view) {

		Set<Interest> interesi = e.getInterests();

		int counterDolazim = 0;
		int counterMozdaDolazim = 0;
		int counterNeDolazim = 0;
		for (Interest i : interesi) {
			switch (i.getInterest().getId()) {
			case (1):
				counterDolazim++;
				break;
			case (2):
				counterMozdaDolazim++;
				break;
			case (3):
				counterNeDolazim++;
				break;
			}
		}
		view.setCounterDolazim(counterDolazim);
		view.setCounterMozdaDolazim(counterMozdaDolazim);
		view.setCounterNeDolazim(counterNeDolazim);

	}

	@RequestMapping(value = "/showEvent/{id}", method = RequestMethod.GET)
	public String showEventGetMethod(@PathVariable String id, Model model, HttpSession session,
			@ModelAttribute("reviewForm") ReviewForm reviewFormRedirect, BindingResult result,
			@ModelAttribute("isWriting") String isWriting) {
		
		Event event = null;
		try {
			event = eventService.getEvent(Integer.parseInt(id));
		} catch (NumberFormatException ex) {
			event = null;
		}

		if (event == null) {
			return "redirect:/home";
		}
		// za dodat
		if ((new Date().getTime() - event.getDateStop().getTime() < TIME_IN_MILLIS_BEFORE_CLOSING_REVIEWS)
				&& (new Date().getTime() - event.getDateStop().getTime() > 0)) {
			model.addAttribute("canComment", "true");
		
		} else {
			
			model.addAttribute("canComment", "false");
		}
		//System.out.println("DATUM ZAVRSETKA JE" +new Date(new Date().getTime() + 1 * HOUR));
		
		
		//Date nowDate=new Date(new Date().getTime() + 1 * HOUR);//heroku
		Date nowDate=new Date();
	
		
		if (event.getDateStop().before(nowDate)) {
			model.addAttribute("hasFinished", "true");
		} else {
			model.addAttribute("hasFinished", "false");
		}

		if (event.getDateStart().after(nowDate)) {
			model.addAttribute("hasStarted", "false");
		} else {
			model.addAttribute("hasStarted", "true");
		}

		EventView eventView = new EventView();
		eventView.fillData(event);
		model.addAttribute("event", eventView);

		List<InterestType> interests = interestTypeService.getAllInterests();
		model.addAttribute("interestTypes", interests);

		User user = (User) session.getAttribute("loggedInUser");
		model.addAttribute("isVisitor", (user instanceof Visitor) ? "true" : "false");

		List<Review> reviews = reviewService.getAllReviewsByEventId(event);
		List<ReviewView> reviewViews = new ArrayList<>();

		// review i interest
		if (user instanceof Visitor) {
			Optional<Review> optionalReview = reviewService.getReviewByVisitorForEvent((Visitor) user, event);
			Review reviewForVisitorOnEvent = null;
			if (optionalReview.isPresent()) {
				reviewForVisitorOnEvent = optionalReview.get();
				ReviewView reviewView = new ReviewView();
				reviewView.fillData(reviewForVisitorOnEvent);
				model.addAttribute("myReview", reviewView);
				reviews.remove(reviewForVisitorOnEvent);
				reviewFormRedirect.fillForm(reviewForVisitorOnEvent);

			}

			model.addAttribute("hasReview", (reviewForVisitorOnEvent != null) ? "true" : "false");

			Optional<Interest> optionalInterest = interestService.getInterest(event, (Visitor) user);
			Interest interestForVisitorOnEvent = null;
			if (optionalInterest.isPresent()) {
				interestForVisitorOnEvent = optionalInterest.get();
				model.addAttribute("myInterest", interestForVisitorOnEvent);
			}
			model.addAttribute("hasInterest", (interestForVisitorOnEvent != null) ? "true" : "false");
		} else {
			model.addAttribute("hasReview", "false");
		}
		for (Review r : reviews) {
			ReviewView reviewView = new ReviewView();
			reviewView.fillData(r);
			reviewViews.add(reviewView);
		}
		model.addAttribute("reviews", reviewViews);

		if (isWriting == null || isWriting.isEmpty()) {
			model.addAttribute("isWriting", "false");
		} else {
			model.addAttribute("isWriting", isWriting);
			if (isWriting == "true") {
				reviewFormValidator.validate(reviewFormRedirect, result);
			}
		}

		model.addAttribute("id", id);
		model.addAttribute("reviewForm", reviewFormRedirect);

		return "/showEvent";
	}

	@RequestMapping(value = "/deleteEvent/{id}", method = RequestMethod.GET)
	public String deleteEventGetMethod(@PathVariable String id, Model model) {
		// tu se treba sredit baza
		Event event = eventService.getEvent(Integer.parseInt(id));
		
		eventService.deleteEvent(event);
		return "redirect:/showEvents/"+event.getOrganiser().getId();
	}

	@RequestMapping(value = "/showEvents/{id}", method = RequestMethod.GET)
	public String showEventsGetMethod(@PathVariable String id, Model model) {
		// tu treba dodat ak nije organiser da ga izbacimo ća

		FilterForm filter = new FilterForm();
		model.addAttribute("filter", filter);

		Organiser user = organiserService.findById(Integer.parseInt(id));
		if (user == null) {
			return "redirect:/home";
		}
		if (user.getAccountValidUntil().before(new Date())) {
			model.addAttribute("isValid", "false");
		} else {
			model.addAttribute("isValid", "true");
		}
		model.addAttribute("user", user);
		model.addAttribute("isOrganiser", "true");
		model.addAttribute("isVisitor", "false");
		Set<Event> eventi = ((Organiser) user).getEvents();
		List<Event> eventiList = new ArrayList<>();

		eventiList.addAll(eventi);

		prikaziEvente(eventiList, model);

		return "/showEvents";
	}

	@RequestMapping(value = "/showEvents/interest/{id}", method = RequestMethod.GET)
	public String showEventsInterestGetMethod(@PathVariable String id, Model model) {

		FilterForm filter = new FilterForm();
		model.addAttribute("filter", filter);

		Visitor visitor = visitorService.findById(Integer.parseInt(id));
		if (visitor == null) {
			return "redirect:/home";
		}
		model.addAttribute("user", visitor);
		model.addAttribute("isOrganiser", "false");
		model.addAttribute("isVisitor", "true");

		List<Event> eventi = new ArrayList<>();

		List<Interest> interesi = interestService.getInterestsForVisitor(visitor);

		for (Interest i : interesi) {
			eventi.add(i.getVisitorEventId().getEvent());
		}

		prikaziEvente(eventi, model);

		return "/showEvents";
	}

	@RequestMapping(value = "/showEvents/interest/{id}", method = RequestMethod.POST)
	public String showEventsInterestPostMethod(@PathVariable String id, Model model,
			@ModelAttribute("filter") FilterForm filter, @ModelAttribute("sort") String sort) {

		// model.addAttribute("filter", filter);
		model.addAttribute("sort", sort);
		Visitor visitor = visitorService.findById(Integer.parseInt(id));

		// model.addAttribute("organiser", visitor);

		Set<Event> eventi = new HashSet<>();

		List<Interest> interesi = interestService.getInterestsForVisitor(visitor);

		for (Interest i : interesi) {
			eventi.add(i.getVisitorEventId().getEvent());
		}

		filtriraj(eventi, filter, model, filter.getSort());

		return "/showEvents";
	}

	@RequestMapping(value = "/showEvents/review/{id}", method = RequestMethod.GET)
	public String showEventsReviewGetMethod(@PathVariable String id, Model model) {

		FilterForm filter = new FilterForm();
		model.addAttribute("filter", filter);

		Visitor visitor = visitorService.findById(Integer.parseInt(id));
		if (visitor == null) {
			return "redirect:/home";
		}
		model.addAttribute("user", visitor);
		model.addAttribute("isOrganiser", "false");
		model.addAttribute("isVisitor", "true");

		List<Event> eventi = new ArrayList<>();

		List<Review> reviews = reviewService.getReviewForVisitor(visitor);

		for (Review r : reviews) {
			eventi.add(r.getVisitorEvent().getEvent());
		}

		prikaziEvente(eventi, model);

		return "/showEvents";
	}

	@RequestMapping(value = "/showEvents/review/{id}", method = RequestMethod.POST)
	public ModelAndView showEventsReviewPostMethod(@PathVariable String id, Model model,
			@ModelAttribute("filter") FilterForm filter, @ModelAttribute("sort") String sort) {
		// model.addAttribute("filter", filter);
		model.addAttribute("sort", sort);

		Visitor visitor = visitorService.findById(Integer.parseInt(id));

		// model.addAttribute("organiser", visitor);

		Set<Event> eventi = new HashSet<>();

		List<Review> reviews = reviewService.getReviewForVisitor(visitor);

		for (Review r : reviews) {
			eventi.add(r.getVisitorEvent().getEvent());
		}

		filtriraj(eventi, filter, model, filter.getSort());

		// return "/showEvents";
		return new ModelAndView("/showEvents", "filter", filter);

	}

	public void filtriraj(Set<Event> eventi, FilterForm filter, Model model, String sort) {
		List<Event> filtriraniEventi = new ArrayList<>();
		Date today = new Date();
		boolean dobarVrijemeDefault;
		boolean dobarTrajanjeDefault;

		if (!filter.isProsli() && !filter.isBuduci()) {
			dobarVrijemeDefault = true;
		} else {
			dobarVrijemeDefault = false;
		}

		if (!filter.isTrajanje0do48() && !filter.isTrajanje48do7() && !filter.isTrajanje7doDalje()) {
			dobarTrajanjeDefault = true;
		} else {
			dobarTrajanjeDefault = false;
		}

		boolean dobarVrijeme;
		boolean dobarTrajanje;
		for (Event e : eventi) {

			dobarVrijeme = dobarVrijemeDefault;
			dobarTrajanje = dobarTrajanjeDefault;

			if (filter.isBuduci() && e.getDateStop().after(today)) {
				dobarVrijeme = true;
			}

			if (filter.isProsli() && e.getDateStop().before(today)) {
				dobarVrijeme = true;
			}

			LocalDate d1 = e.getDateStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate d2 = e.getDateStop().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			// Za sad sam uspio ovo rjesiti samo ovako. Nazalost filtriranje ne gleda tocno
			// vrijeme nego samo datume. Pokusat cu to rjesiti sto prije.
			Duration d = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
			if (filter.isTrajanje0do48() && d.toDays() <= 2) {
				dobarTrajanje = true;
			}

			if (filter.isTrajanje48do7() && d.toDays() > 2 && d.toDays() < 7) {
				dobarTrajanje = true;
			}

			if (filter.isTrajanje7doDalje() && d.toDays() > 7) {
				dobarTrajanje = true;
			}

			if (dobarVrijeme && dobarTrajanje) {
				filtriraniEventi.add(e);
			}
		}

		// znam ovo je uzasno
		String popularniSort = "";

		switch (sort) {

		case ("aktualni"):
			break;
		case ("datumSilazno"):
			Collections.sort(filtriraniEventi, (a, b) -> a.getDateStart().after(b.getDateStart()) ? -1 : 1);
			break;
		case ("datumUzlazno"):
			Collections.sort(filtriraniEventi, (a, b) -> a.getDateStart().before(b.getDateStart()) ? -1 : 1);
			break;
		case ("najpopularniji"):
			popularniSort = sort; // jasno mi je da je ovo stvarno uzas
			break;
		case ("najnepopularniji"):
			popularniSort = sort; // znam znam, katastroficno lose
			break;
		case ("abecednoSilazno"):
			Collections.sort(filtriraniEventi, (a, b) -> a.getName().compareTo(b.getName()));
			break;
		case ("abecednoUzlazno"):
			Collections.sort(filtriraniEventi, (a, b) -> b.getName().compareTo(a.getName()));
		}

		prikaziEvente(filtriraniEventi, model, popularniSort);

	}

	public void prikaziEvente(List<Event> eventi, Model model) {
		List<EventView> prikaz = new ArrayList<>();

		for (Event e : eventi) {
			EventView view = new EventView();
			view.fillData(e);
			prebrojiInterese(e, view);
			prikaz.add(view);
		}

		model.addAttribute("eventi", prikaz);
	}

	/**
	 * Svjestan sam da je ovaj nacin sortiranja popularnosti uzasna implementacija
	 * ali bilo je puno lakse ovako napraviti nego cijelu strukturu promjeniti
	 *
	 * Preuzimam svu krivnju
	 *
	 * @author Dvus
	 *
	 * @param eventi
	 * @param model
	 * @param popularniSort
	 */
	public void prikaziEvente(List<Event> eventi, Model model, String popularniSort) {
		List<EventView> prikaz = new ArrayList<>();

		for (Event e : eventi) {
			EventView view = new EventView();
			view.fillData(e);
			prebrojiInterese(e, view);
			prikaz.add(view);
		}

		if (popularniSort.equals("najpopularniji")) {
			Collections.sort(prikaz, (a, b) -> Integer.compare(b.getCounterDolazim(), a.getCounterDolazim()));
		} else if (popularniSort.equals("najnepopularniji")) {
			Collections.sort(prikaz, (a, b) -> Integer.compare(b.getCounterNeDolazim(), a.getCounterNeDolazim()));
		}

		model.addAttribute("eventi", prikaz);
	}



	private static class SendMailJob implements Runnable {
		private String category;
		private String area;
		private String organiser;
		private String[] mail;
		private JavaMailSender javaMailSender;
		
		public SendMailJob(String category, String area, String organiser, String[] mail,JavaMailSender javaMailSender) {
			super();
			this.category = category;
			this.area = area;
			this.organiser = organiser;
			this.mail = mail;
			this.javaMailSender=javaMailSender;
		}

		public void run() {
			sendEmail(category, area, organiser, mail);
		}

		public void sendEmail(String category, String area, String organiser, String[] mail) {

			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(mail);

			msg.setSubject("Dodan je novi događaj");
			msg.setText("Novi događaj je dodan od korisnika " + organiser + ".\n Kategorija: " + category + ". Područje: "
					+ area);

			javaMailSender.send(msg);

		}
	}

}
