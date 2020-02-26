package hr.fer.opp.webmeisters.service;

import hr.fer.opp.webmeisters.dao.CategoryRepository;
import hr.fer.opp.webmeisters.dao.EventRepository;
import hr.fer.opp.webmeisters.dao.InterestRepository;
import hr.fer.opp.webmeisters.dao.ReviewRepository;
import hr.fer.opp.webmeisters.data.form.EventForm;
import hr.fer.opp.webmeisters.data.model.*;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private AreaService areaService;
    
    

    @Transactional
    public Event createEvent(EventForm form, Organiser org){

        Event event = new Event();

        event.setOrganiser(org);

        event.setName(form.getName());

        event.setAddress(form.getAddress());

        

        event.setCategory(categoryService.findByName(form.getCategory()));
        event.setArea(areaService.findByName(form.getArea()));
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTimeStart = LocalDateTime.parse(form.getStart(), format);
        LocalDateTime dateTimeStop = LocalDateTime.parse(form.getStop(), format);
        Date dateStart = Date.from(dateTimeStart.atZone(ZoneId.systemDefault()).toInstant());
        Date dateEnd = Date.from(dateTimeStop.atZone(ZoneId.systemDefault()).toInstant());
        event.setDateStart(dateStart);
        event.setDateStop(dateEnd);

        event.setPictures(form.getPictures());
        event.setVideos(form.getVideos());

        event.setDescription(form.getDescription());
        
       

        return eventRepository.save(event);
    }



	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}



	public Event getEvent(int parseInt) {
		return eventRepository.getOne(parseInt);
	}



	public void deleteEvent(Event event) {
        List<Interest> interesi = interestRepository.findByVisitorEventIdEvent(event);
        List<Review> reviews = reviewRepository.findByVisitorEventEvent(event);
        for(Interest i : interesi){
            interestRepository.delete(i);
        }

        for(Review r : reviews){
            reviewRepository.delete(r);
        }

        eventRepository.delete(event);
		
	}

}
