package TheBettersAPIServices.TheBettersAPIServices.dto.Users;

public class SearchUsersResponse {
        
    private String idUser;
    private String Nombre;
    private String Email;
    private String Password;
    private boolean IsExist = false;



    public String getidUser() {
        return this.idUser;
    }

    public void setidUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNombre() {
        return this.Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
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
    public boolean getIsExist() {
        return this.IsExist;
    }

    public void setIsExist(Boolean IsExist) {
        this.IsExist = IsExist;
    }

    @Override
    public String toString() {
        return "{" +
                " idUser='" + getidUser() + "'" +
                " Nombre='" + getNombre() + "'" +
                " Email='" + getEmail() + "'" +
                " IsExist='" + getIsExist() + "'" +
                ", Password='" + getPassword() + "'" +
                "}";
    }

}
