package TheBettersAPIServices.TheBettersAPIServices.Utilities;
import java.util.ArrayList;
import TheBettersAPIServices.TheBettersAPIServices.dto.ReturnValidators;
public class Validations {

    public ArrayList State = new ArrayList();
    ReturnValidators _raturnvalid = new ReturnValidators();
    public int NumErrors = 0;
    public ReturnValidators ValidationsData(String Field, String NameField, DataType.Type _type, boolean IsRequired) {
        DataType _validation = new DataType();
        Boolean IsValidVar = null;
        if (IsRequired) {
            if (!IsEmpty(Field)) {
                IsValidVar = _validation.ValidDatatype(Field, _type);
                if (!IsValidVar) {
                    NumErrors++;
                    _raturnvalid.setErrorDesc(NameField + " not a correct data type");
                }
            } else {
                _raturnvalid.setErrorDesc(NameField + " is required");
                NumErrors++;
            }
        } else {

        } 
        _raturnvalid.setErrors(NumErrors);
    return _raturnvalid;
    }
    public boolean IsEmpty(String expression) /* Unicamente s edebe usar para validar campos de contraseña */
    {
        boolean isValid = true;
        if (expression != "" || expression != null) {
            isValid = false;
        }
        return isValid;
    }
    public boolean IsEmptyObject(Object expression){
        boolean isValid = true;
        if (expression != "" || expression != null) {
            isValid = false;
        }
        return isValid;
    }
    public boolean IsNumber(String expression) /* Unicamente s edebe usar para validar campos de contraseña */
    {
        boolean isValid = false;
        if (expression != null && expression != "" && expression != "null" && expression != "undefined")
        {
            String regex = "^[0-9]+$";
            isValid = expression.matches(regex);
        }
        return isValid;
    }


}

