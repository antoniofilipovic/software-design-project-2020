package hr.fer.opp.webmeisters.data.model;

import javax.persistence.*;

@Entity
@Table(name = "interestTypes")
public class InterestType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="interestType_id")
    private int id;

    @Column(name="interestType_name")
    private String name;
    
    public InterestType() {
    	
    }

	public InterestType(String interestType) {
		this.name=interestType;
	}

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
    
    
}
