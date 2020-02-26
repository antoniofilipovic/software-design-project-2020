package hr.fer.opp.webmeisters.data.form;

import hr.fer.opp.webmeisters.data.model.Organiser;

//@PasswordMatches()
public class OrganiserForm {

   // @NotEmpty(message = "Name can't be empty")
    private String name;

  //  @Column(length = 100, nullable = false, unique = true)
    //@ValidEmail
    private String email;

    //@NotEmpty()
    private String password;

    //@NotEmpty()
    private String repassword;

    //@NotEmpty(message = "Address can't be empty")
    private String realAddress;
    
    
    private String webAddress;
    


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getWebAddress() {
        return webAddress;
    }
    

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getRealAddress() {
		return realAddress;
	}

	public void setRealAddress(String realAddress) {
		this.realAddress = realAddress;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public void fillData(Organiser organiser) {
		this.email=organiser.getEmail();
		this.name=organiser.getFullName();
		this.realAddress=organiser.getRealAddress();
		this.webAddress=organiser.getWebAddress();
		
	}


    
    
}
