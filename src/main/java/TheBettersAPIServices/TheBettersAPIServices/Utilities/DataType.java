package TheBettersAPIServices.TheBettersAPIServices.Utilities;

public class DataType {

    public enum Type {
        IsGUID,
        IsString,
        IsMail,
        IsEmptyOrNull,
        IsCurp,
        IsRfc,
        IsPassword,
        IsOnlyNumber,
        IsPhone,
        IsHeadersString,
        IsBodyString,
        IsBodyNumber,
        IsNames
    }

    public Boolean ValidDatatype(String ValVariable,Type _Datatype) {
        Boolean IsValid = false;
        switch(_Datatype) { 
            case IsGUID:
                IsValid = IsGUID(ValVariable) ? true : false;
                break;
            case IsString:
                IsValid = IsString(ValVariable) ? true : false;
                break;
            case IsCurp:
                IsValid = isCurp(ValVariable) ? true : false;
                break;
            case IsRfc:
                IsValid = isRfc(ValVariable) ? true : false;
                break;
            case IsMail:
                IsValid = IsMail(ValVariable) ? true : false;
                break;
            case IsPassword:
                IsValid = IsPassword(ValVariable) ? true : false;
                break;
            case IsOnlyNumber:
                IsValid = IsOnlyNumber(ValVariable) ? true : false;
                break;
            case IsHeadersString:
                IsValid = IsHeadersString(ValVariable) ? true : false;
                break;
            case IsBodyString:
                IsValid = IsBodyString(ValVariable) ? true : false;
                break;
            case IsBodyNumber:
                IsValid = IsBodyNumber(ValVariable) ? true : false;
                break;
            case IsPhone:
                IsValid = IsPhone(ValVariable) ? true : false;
                break; 
            case IsNames:
                IsValid = IsNames(ValVariable) ? true : false;
                break; 
            default:
                IsValid = false;
                break;
        }
        return IsValid;
    }

    public boolean IsGUID(String expression) /* Para comprobrar si se trata de un GUID */
    {
        boolean isValid = false;
        boolean isEmpty = isEmpty(expression);
        if (!isEmpty)
        {
            String regex = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
            isValid = expression.matches(regex);
        }
        return isValid;
    }


    public boolean IsPassword(String expression) /* Unicamente s edebe usar para validar campos de contraseña */
    {
        boolean isValid = false;
        if (isEmpty(expression))
        {
            String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$";
            isValid = expression.matches(regex);
        }
        return isValid;
    }

    public boolean isCurp(String expression) /* Unicamente s edebe usar para validar campos de contraseña */
    {
        boolean isValid = false;
        if (!isEmpty(expression))
        {
            String regex = "^[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9]{2}$";
            isValid = expression.matches(regex);
        }
        return isValid;
    }
    public boolean isRfc(String expression) /* Unicamente s edebe usar para validar campos de contraseña */
    {
        boolean isValid = false;
        if (!isEmpty(expression))
        {
            String regex = "^[A-Z]{4}[0-9]{6}[A-Z0-9]{3}$";
            isValid = expression.matches(regex);
        }
        return isValid;
    }
    public boolean IsEmpty(String expression) /* Unicamente s edebe usar para validar campos de contraseña */
    {
        boolean isValid = false;
        if (expression != "" && expression != null && expression != "null" && expression != "undefined") {
            isValid = true;
        }
        return isValid;
    }
    public static Boolean toBoolean(String string) {
        return Boolean.valueOf(string);
    }

    public boolean IsString(String expression) /* Unicamente s edebe usar para validar campos de contraseña */
    {
        boolean isValid = false;
        if (isEmpty(expression))
        {
            String regex = "^[a-zA-Z0-9]+$";
            isValid = expression.matches(regex);

        }
        return isValid;
    }
    public boolean IsNames(String expression) /* Unicamente s edebe usar para validar campos de contraseña */
    {
        boolean isValid = false;
        if (isEmpty(expression))
        {
            String regex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";
            isValid = expression.matches(regex);

        }
        return isValid;
    }

    public boolean IsPhone(String expression) {
        boolean isValid = false;
        if (isEmpty(expression) && IsOnlyNumber(expression) && expression.length() == 10) {
            isValid = true;
        }
        return isValid;
    }
    public boolean isEmpty(String expression) {
        return expression != null && expression != "null" && expression != "" && expression != "undefined";
    }
    public boolean IsHeadersString(String expression) {
        boolean isValid = false;
        if (expression != null && expression != "null" && expression != "" && expression != "undefined"
                && IsString(expression)) {
            isValid = true;
        }
        return isValid;
    }
    public boolean IsBodyString(String expression) {
        boolean isValid = false;
        if (isEmpty(expression) && IsString(expression)) {
            isValid = true;
        }
        return isValid;
    }
    public boolean IsOnlyNumber(String expression) /* Unicamente s edebe usar para validar campos de contraseña */
    {
        boolean isValid = false;
        if (isEmpty(expression))
        {
            String regex = "^[0-9]+$";
            isValid = expression.matches(regex);
        }
        return isValid;
    }
    public boolean IsBodyNumber(String expression) {
        boolean isValid = false;
        if (isEmpty(expression) && IsOnlyNumber(expression)) {
            isValid = true;
        }
        return isValid;
    }
    public boolean IsMail(String expression) /* Unicamente s edebe usar para validar campos de contraseña */
    {
        boolean isValid = false;
        if (isEmpty(expression))
        {
            String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            isValid = expression.matches(regex);
        }
        return isValid;
    }
}

