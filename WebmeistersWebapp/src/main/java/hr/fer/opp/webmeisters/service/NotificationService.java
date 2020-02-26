package hr.fer.opp.webmeisters.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hr.fer.opp.webmeisters.data.form.VisitorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.webmeisters.dao.NotificationRepository;
import hr.fer.opp.webmeisters.data.form.NotificationForm;
import hr.fer.opp.webmeisters.data.model.Area;
import hr.fer.opp.webmeisters.data.model.Category;
import hr.fer.opp.webmeisters.data.model.Notification;
import hr.fer.opp.webmeisters.data.model.Visitor;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private AreaService areaService;
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private VisitorService visitorService;
	
	public void save(NotificationForm notificationForm, Visitor visitor) {
		Notification notification =new Notification();
		Set<Area> areas=new HashSet<>();
		for(String area:notificationForm.getAreas()) {
			areas.add(areaService.findByName(area));
			
		}
		notification.setAreas(areas);
		
		Set<Category> categories=new HashSet<>();
		for(String category:notificationForm.getCategories()) {
			categories.add(categoryService.findByName(category));
			
		}
		notification.setCategories(categories);
		
		notification.setVisitor(visitor);
		notificationRepository.save(notification);
		visitor.setNotification(notification);
		visitorService.update(visitor);
		
	}

	public Notification findById(int parseInt) {
		return notificationRepository.getOne(parseInt);
	}

	public void delete(Notification notification, Visitor visitor) {

		visitorService.removeNotificationFromVisitor(visitor);
		notificationRepository.delete(notification);
		
	}
	
	public List<Notification> getAll(){
		return notificationRepository.findAll();
	}


	

}
