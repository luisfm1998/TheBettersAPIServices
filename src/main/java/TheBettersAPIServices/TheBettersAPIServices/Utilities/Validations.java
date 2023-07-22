package TheBettersAPIServices.TheBettersAPIServices.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;

import TheBettersAPIServices.TheBettersAPIServices.dto.ReturnValidators;

public class Validations {

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

    public boolean IsEmptyObject(Object expression) {
        boolean isValid = true;
        if (expression != "" || expression != null) {
            isValid = false;
        }
        return isValid;
    }

    public boolean IsNumber(String expression) /* Unicamente s edebe usar para validar campos de contraseña */
    {
        boolean isValid = false;
        if (expression != null && expression != "" && expression != "null" && expression != "undefined") {
            String regex = "^[0-9]+$";
            isValid = expression.matches(regex);
        }
        return isValid;
    }

    public List<Object> Sql(Map<String, Object> _result) {
        List<Object> data = new ArrayList<>();
        Object objeto;
        if (_result.size() > 0) {
            for (int i = 1; i <= _result.size(); i++) {
                if (_result.get("#result-set-" + i + "") != "" && _result.get("#result-set-" + i + "") != null &&
                        _result.get("#result-set-" + i + "") != "null"
                        && _result.get("#result-set-" + i + "") != "[]") {
                    objeto = _result.get("#result-set-" + i + "");
                    data.add(objeto);
                }
            }
        }
        return data;
    }

    public String ToJson(Object _response) throws JsonMappingException, JsonProcessingException {
        Gson Json = new Gson();
        String _reponsejson = Json.toJson(_response);    
        return _reponsejson;
    }
}
