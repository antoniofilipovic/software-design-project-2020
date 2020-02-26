package hr.fer.opp.webmeisters.data.view;

import java.util.ArrayList;
import java.util.List;

import hr.fer.opp.webmeisters.data.model.Event;
import hr.fer.opp.webmeisters.data.model.Picture;
import hr.fer.opp.webmeisters.data.model.Video;

public class EventView {
	private int organiserId;
    private int eventId;
    private String thumbnail;
    private String eventName;
    private String eventCategory; //ispis
    private String organiserName;
    private String dateStart;
    private String dateStop;
    
    private String category;
    private String area;
    
    private List<String> pictures;
    private List<String> videos;
    private String address;
    private String description;
    
	private int counterDolazim;
	private int counterMozdaDolazim;
	private int counterNeDolazim;

    
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	
    public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}

	public List<String> getPictures() {
		return pictures;
	}

	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
	}

	public List<String> getVideos() {
		return videos;
	}

	public void setVideos(List<String> videos) {
		this.videos = videos;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrganiserName() {
        return organiserName;
    }

    public void setOrganiserName(String organiserName) {
        this.organiserName = organiserName;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateStop() {
        return dateStop;
    }

    public void setDateStop(String dateStop) {
        this.dateStop = dateStop;
    }

    public int getOrganiserId() {
        return organiserId;
    }

    public void setOrganiserId(int organiserId) {
        this.organiserId = organiserId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

	public int getCounterDolazim() {
		return counterDolazim;
	}

	public void setCounterDolazim(int counterDolazim) {
		this.counterDolazim = counterDolazim;
	}

	public int getCounterMozdaDolazim() {
		return counterMozdaDolazim;
	}

	public void setCounterMozdaDolazim(int counterMozdaDolazim) {
		this.counterMozdaDolazim = counterMozdaDolazim;
	}

	public int getCounterNeDolazim() {
		return counterNeDolazim;
	}

	public void setCounterNeDolazim(int counterNeDolazim) {
		this.counterNeDolazim = counterNeDolazim;
	}

	public void fillData(Event event) {
		
		 if (event.getPictures().isEmpty()) {
             setThumbnail("no-image-found-360x260.png");
         } else {
             setThumbnail(event.getPictures().get(0).getPathName());
         }

		setEventId(event.getId());
		setAddress(event.getAddress());
		setDateStart(event.getDateStart().toLocaleString());
		setDateStop(event.getDateStop().toLocaleString());
		setDescription(event.getDescription());
		
		setCategory(event.getCategory().getName());
		setArea(event.getArea().getName());
		
		
		//eventView.setEventCategory(event.getca);
		setEventName(event.getName());
		setOrganiserId(event.getOrganiser().getId());
		setOrganiserName(event.getOrganiser().getFullName());
		
		List<String> pictures=new ArrayList<>();
		
		for(Picture p:event.getPictures()) {
			pictures.add(p.getPathName());
		}
		setPictures(pictures);

		List<String> videos = new ArrayList<>();

		for(Video v : event.getVideos()){
			videos.add(v.getPathName());
		}

		setVideos(videos);
		
	}
}
