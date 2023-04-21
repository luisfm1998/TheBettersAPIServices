package TheBettersAPIServices.TheBettersAPIServices.dto;

public class Login {
    
    private String Email;
    private String Password;
    public Login() {
        // No hace nada
    }


    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String Password) {
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

