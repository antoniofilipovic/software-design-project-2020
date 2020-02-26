package hr.fer.opp.webmeisters.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hr.fer.opp.webmeisters.dao.InterestRepository;
import hr.fer.opp.webmeisters.dao.NotificationRepository;
import hr.fer.opp.webmeisters.dao.ReviewRepository;
import hr.fer.opp.webmeisters.dao.UserRepository;
import hr.fer.opp.webmeisters.dao.VisitorRepository;
import hr.fer.opp.webmeisters.data.form.EditInfoVisitorForm;
import hr.fer.opp.webmeisters.data.form.VisitorForm;
import hr.fer.opp.webmeisters.data.model.Interest;
import hr.fer.opp.webmeisters.data.model.Review;
import hr.fer.opp.webmeisters.data.model.User;
import hr.fer.opp.webmeisters.data.model.Visitor;
import hr.fer.opp.webmeisters.exception.EmailExistsException;
import hr.fer.opp.webmeisters.util.PasswordUtil;

@Service
public class VisitorService implements IVisitorService {

	@Autowired
	private VisitorRepository visitorRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private InterestRepository interestRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Transactional
	public Visitor registerNewVisitorAccount(VisitorForm visitorForm) throws EmailExistsException {

		if (emailExists(visitorForm.getEmail())) {
			throw new EmailExistsException();
		}
		Visitor visitor = new Visitor();
		visitor.setFullName(visitorForm.getFullName());
		visitor.setEmail(visitorForm.getEmail());
		visitor.setPasswordHash(PasswordUtil.passwordEncrypt(visitorForm.getPassword()));
		visitor.setNick(visitorForm.getNick());
		visitor.setUserType("visitor");
		return visitorRepository.save(visitor);
	}

	private boolean emailExists(String email) {

		User user = userRepository.findByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}

	public Visitor findByEmail(String email) {
		return visitorRepository.findByEmail(email);
	}

	public Visitor findByNick(String nick) {
		return visitorRepository.findByNick(nick);
	}

	public Visitor findById(int id) {
		return visitorRepository.getOne(id);
	}

	public Visitor updateVisitorAccount(Visitor oldVisitor, EditInfoVisitorForm visitorForm) {
		oldVisitor.setEmail(visitorForm.getEmail());
		oldVisitor.setFullName(visitorForm.getFullName());

		oldVisitor.setNick(visitorForm.getNick());

		if (!visitorForm.getPassword().isEmpty()) {
			oldVisitor.setPasswordHash(PasswordUtil.passwordEncrypt(visitorForm.getPassword()));

		}

		visitorRepository.save(oldVisitor);
		return oldVisitor;
	}

	public boolean isVisitor(int id) {
		if (visitorRepository.getOne(id) instanceof Visitor) {
			return true;
		}
		return false;
	}

	public Visitor getVisitor(int id) {
		return visitorRepository.getOne(id);
	}

	public void update(Visitor visitor) {
		visitorRepository.save(visitor);

	}

	public Visitor removeNotificationFromVisitor(Visitor visitor) {
		visitor.setNotification(null);
		visitorRepository.save(visitor);

		return visitor;
	}

	public void deleteVisitor(int parseInt) {
		Visitor v = visitorRepository.getOne(parseInt);
		if(v.getNotification()!=null) {

			notificationService.delete(v.getNotification(),v);
			
		}

		List<Review> reviews = new ArrayList<>(v.getReviews());
		List<Interest> interests = new ArrayList<>(v.getInterests());

		for (Interest i : interests) {
			interestRepository.delete(i);
		}

		for (Review r : reviews) {
			reviewRepository.delete(r);
		}
		visitorRepository.delete(v);

	}
}
