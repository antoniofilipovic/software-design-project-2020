package hr.fer.opp.webmeisters.data.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "events")

@OnDelete(action = OnDeleteAction.CASCADE)
public class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_id")
	private int id;
	
	@Column(name="event_name")
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
	private Organiser organiser;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "event_id", referencedColumnName = "event_id")})
	private Set<Interest> interests;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = true)
	private Category category;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", nullable = true)
	private Area area;
	
	
	
	@Column(name = "date_start_at",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStart;
	
	
	@Column(name = "date_stop_at",nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStop;
	
	@Column(name = "address")
	private String address;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "event_pictures", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "picture_id"))
	private List<Picture> pictures;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "event_videos", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "video_id"))
	private List<Video> videos;

	@Column(name = "description")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Organiser getOrganiser() {
		return organiser;
	}

	public void setOrganiser(Organiser organiser) {
		this.organiser = organiser;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Interest> getInterests() {
		return interests;
	}

	public void setInterests(Set<Interest> interests) {
		this.interests = interests;
	}



	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateStop() {
		return dateStop;
	}

	public void setDateStop(Date dateStop) {
		this.dateStop = dateStop;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}


