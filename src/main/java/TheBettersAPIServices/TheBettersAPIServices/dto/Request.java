package TheBettersAPIServices.TheBettersAPIServices.dto;

import java.util.List;

public class Request {

    private List<String> Email;
    private List<String> Password;


    public Request(){
        //Empty
    }

    public List<String> getEmail() {
        return this.Email;
    }

    public void setEmail(List<String> Email) {
        this.Email = Email;
    }

    public List<String> getPassword() {
        return this.Password;
    }

    public void setPassword(List<String>  Password) {
        this.Password = Password;
    }
    @Override
    public String toString() {
        return "{" +
                " Email='" + getEmail() + "'" +
                ", Password='" + getPassword() + "'" +
                "}";
    }
}