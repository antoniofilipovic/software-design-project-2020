package hr.fer.opp.webmeisters.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;

@Entity
@Table(name = "pictures")

@OnDelete(action = OnDeleteAction.CASCADE)
public class Picture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="picture_id")
	private int id;
	
	@Column(name="picture_name")
	private String name;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "event_id", nullable = true)
//	private Event event;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPathName(){
		String p = name.substring(name.indexOf('-')+1);
		return p;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Picture picture = (Picture) o;
		return id == picture.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


//	public Event getEvent() {
//		return event;
//	}
//
//	public void setEvent(Event event) {
//		this.event = event;
//	}
	
	
	
	
	

}
