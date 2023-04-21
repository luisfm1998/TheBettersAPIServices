package TheBettersAPIServices.TheBettersAPIServices.Validator;

import org.springframework.stereotype.Component;

import TheBettersAPIServices.TheBettersAPIServices.Exceptions.Apiunauthorized;
import TheBettersAPIServices.TheBettersAPIServices.dto.Request;

@Component
public class AuthValidator {

    private static final String CLIENT_CREDENTIALS = "client_credentials";

    public void validate(Request request) throws Apiunauthorized{
        // if(granType.isEmpty() || !granType.equals(CLIENT_CREDENTIALS)){
        //     message("El campo  grant type  es invalido");
        // }
        if(request == null && request.getEmail().get(0).toString() ==  null && request.getPassword().get(0).toString() == ""){
                message("client_id y/o client_secret son validos");
        } 
    }
    private void message(String message) throws Apiunauthorized{
        throw new Apiunauthorized(message);
    }
    
}