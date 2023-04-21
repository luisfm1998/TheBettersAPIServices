package TheBettersAPIServices.TheBettersAPIServices.dto;

import java.util.ArrayList;

public class Response {
    private Object Data;
    private boolean isOK = false;
    private String Authorization = "";
    private String Message = "";

    public Response() {
        // No hace nada
    }

    public String getAuthorization(){
        return this.Authorization;
    }
    public void setAuthorization(String Authorization) {
        this.Authorization = Authorization;
    }
    public String getMessage(){
        return this.Message;
    }
    public void setMessage(String Message) {
        this.Message = Message;
    }
    public boolean getIsOK(){
        return this.isOK;
    }
    public void setIsOK(Boolean isOK) {
        this.isOK = isOK;
    }

    public Object getData() {
        return this.Data;
    }
    public void setData(Object Data) {
        this.Data = Data;
    }
    public String toString() {
        return "{" +
                "Data='" + getData() + "'" +
                ", Authorization='" + getAuthorization() + "'" +
                ", isOK='" + getIsOK() + "'" +
                "}";
    }
}

