package TheBettersAPIServices.TheBettersAPIServices.dto;

public class ReturnValidators {
    
    private int Errors;
    private String ErrorDesc;

    public int getErrors() {
        return this.Errors;
    }

    public void setErrors(Integer Errors) {
        this.Errors = Errors;
    }

    public String getErrorDesc() {
        return this.ErrorDesc;
    }

    public void setErrorDesc(String ErrorDesc) {
        this.ErrorDesc = ErrorDesc;
    }

    public String toString() {
        return "{" +
                " Errors='" + getErrors() + "'" +
                ", ErrorDesc='" + getErrorDesc() + "'" +
                "}";
    }
}
