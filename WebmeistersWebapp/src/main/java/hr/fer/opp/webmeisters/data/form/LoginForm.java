package hr.fer.opp.webmeisters.data.form;


public class LoginForm {

    private String email;
    private String password;

    public LoginForm(String email, String pass){
        this.email = email;
        password = pass;
    }

    public LoginForm(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
