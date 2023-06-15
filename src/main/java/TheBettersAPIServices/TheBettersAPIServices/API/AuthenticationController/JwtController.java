package TheBettersAPIServices.TheBettersAPIServices.API.AuthenticationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TheBettersAPIServices.TheBettersAPIServices.Exceptions.Apiunauthorized;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "API")
public class JwtController {
    @Autowired
    private TheBettersAPIServices.TheBettersAPIServices.Services.AuthenticationServices.AuthService authService;
    
    @PostMapping(path = "oauth/clien_credential/services", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> Serv(@RequestBody MultiValueMap<String, String> paramMap) throws Apiunauthorized{
        return ResponseEntity.ok(authService.Serv());
    }  

}