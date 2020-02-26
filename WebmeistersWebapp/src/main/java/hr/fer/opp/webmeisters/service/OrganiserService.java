package hr.fer.opp.webmeisters.service;

import hr.fer.opp.webmeisters.dao.EventRepository;
import hr.fer.opp.webmeisters.dao.InterestRepository;
import hr.fer.opp.webmeisters.dao.OrganiserRepository;
import hr.fer.opp.webmeisters.dao.ReviewRepository;
import hr.fer.opp.webmeisters.dao.UserRepository;
import hr.fer.opp.webmeisters.data.form.EditInfoOrganiserForm;
import hr.fer.opp.webmeisters.data.form.OrganiserForm;
import hr.fer.opp.webmeisters.data.model.CreditCard;
import hr.fer.opp.webmeisters.data.model.Event;
import hr.fer.opp.webmeisters.data.model.Interest;
import hr.fer.opp.webmeisters.data.model.Organiser;
import hr.fer.opp.webmeisters.data.model.PayPal;
import hr.fer.opp.webmeisters.data.model.Review;
import hr.fer.opp.webmeisters.data.model.User;
import hr.fer.opp.webmeisters.data.model.Visitor;
import hr.fer.opp.webmeisters.exception.EmailExistsException;
import hr.fer.opp.webmeisters.util.PasswordUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrganiserService implements IOrganiserService {

    @Autowired
    private OrganiserRepository organiserRepository;

    @Autowired
    private UserRepository userRepository;
    
    
    @Autowired
	private EventService eventService;


    @Transactional
    public Organiser registerNewOrganiserAccount(OrganiserForm organiserForm) throws EmailExistsException {

        User user = userRepository.findByEmail(organiserForm.getEmail());

        if(user == null){
            Organiser org = new Organiser();
            org.setEmail(organiserForm.getEmail());
            org.setFullName(organiserForm.getName());
            org.setPasswordHash(PasswordUtil.passwordEncrypt(organiserForm.getPassword()));
            org.setRealAddress(organiserForm.getRealAddress());
            org.setWebAddress(organiserForm.getWebAddress());
            org.setUserType("organiser");
            org.setAccountValidUntil(new Date());

            return organiserRepository.save(org);

        } else {
            throw new EmailExistsException();
        }
    }

	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}


    @Transactional
	public Organiser updateOrganiserAccount(Organiser oldOrganiser, EditInfoOrganiserForm organiserForm) throws EmailExistsException {
    	oldOrganiser.setEmail(organiserForm.getEmail());
    	oldOrganiser.setFullName(organiserForm.getName());
    	
		if(!organiserForm.getPassword().isEmpty()) {
			oldOrganiser.setPasswordHash(PasswordUtil.passwordEncrypt(organiserForm.getPassword()));
		}

    	
    	oldOrganiser.setWebAddress(organiserForm.getWebAddress());
    	oldOrganiser.setRealAddress(organiserForm.getRealAddress());


//    	oldOrganiser.setEmail(organiserForm.getEmail());
//    	oldOrganiser.setEmail(organiserForm.getEmail());
//    	oldOrganiser.setEmail(organiserForm.getEmail());
    	organiserRepository.save(oldOrganiser);
    	return oldOrganiser;

//        User user = userRepository.findByEmail(organiserForm.getEmail());
//
//        if(user == null){
//
//            organiserRepository.delete(oldOrganiser);
//
//            return registerNewOrganiserAccount(organiserForm);
//
//        } else {
//            throw new EmailExistsException();
//        }
    }

    @Transactional
    public Organiser deleteOrganiserAccount(Organiser org) {
        organiserRepository.delete(org);
        return org;
    }

    public Organiser findOrganiser(int id) {
        return organiserRepository.getOne(id);
    }

    public boolean isOrganiser(int id){
        if(organiserRepository.getOne(id) instanceof Organiser){
            return true;
        }
        return false;
    }
    
    public Organiser findById(int id){ return organiserRepository.getOne(id);}

	public void addCreditCard(CreditCard creditCard, Organiser organiser) {
		organiser.setCreditCard(creditCard);
		organiserRepository.save(organiser);
		
	}

	public void addPayPal(PayPal payPal, Organiser organiser) {
		organiser.setPayPal(payPal);
		organiserRepository.save(organiser);
		
	}

	public void deletePayPalForOrganiser(Organiser organiser) {
		organiser.setPayPal(null);
		organiserRepository.save(organiser);
		
	}

	public void deleteCreditCardForOrganiser(Organiser organiser) {
		organiser.setCreditCard(null);
		organiserRepository.save(organiser);
		
	}

	public List<Organiser> getAll() {
		return organiserRepository.findAll();
	}

	public void update(Organiser organiser) {
		organiserRepository.save(organiser);
		
	}

	public void deleteOrganiser(int parseInt) {
		Organiser org=organiserRepository.getOne(parseInt);
		List<Event> events = new ArrayList<>(org.getEvents());
		

		for (Event e : events) {
			eventService.deleteEvent(e);
		}

		
		organiserRepository.delete(org);
		
	}

	
}
