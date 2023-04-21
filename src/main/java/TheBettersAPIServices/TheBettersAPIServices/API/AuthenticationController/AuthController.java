package TheBettersAPIServices.TheBettersAPIServices.API.AuthenticationController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;

import TheBettersAPIServices.TheBettersAPIServices.Exceptions.Apiunauthorized;
import TheBettersAPIServices.TheBettersAPIServices.Utilities.Encryption.JWE;
import TheBettersAPIServices.TheBettersAPIServices.Validator.AuthValidator;
import TheBettersAPIServices.TheBettersAPIServices.dto.Request;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "API")
public class AuthController {

    @Autowired
    private TheBettersAPIServices.TheBettersAPIServices.Services.AuthenticationServices.AuthService authService;

    @Autowired
    private JWE _jwe;

    @Autowired
    private AuthValidator validator;

    @PostMapping(value = "/oauth/clien_credential/accesstoken", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(@RequestBody MultiValueMap<String, String> paramMap) throws Apiunauthorized, SQLException{
        return ResponseEntity.ok(authService.login(paramMap));
    }

    @GetMapping(value = "/oauth/clien_credential/rsageneratekeys", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> GenerateRSAKeys(@RequestBody MultiValueMap<String, String> paramMap) throws NoSuchAlgorithmException, InvalidKeySpecException{
        return ResponseEntity.ok(_jwe.GenerateRSAKeys());
    }


    // @PostMapping(path = "/oauth/clien_credential/accesstoken", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<Object> Serv(@RequestBody MultiValueMap<String, String> paramMap) throws Apiunauthorized{
    //     return ResponseEntity.ok(authService.GetUsuarios(paramMap));
    // }  
    // @PostMapping(value = "/oauth/clien_credential/accesstoken", produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<Object> GetUsuarios(@RequestHeader HttpHeaders httpHeaders, @RequestBody Request Loginrequest){
    //     // validator.validate(request);
    //     return ResponseEntity.ok(authService.GetUsuarios(httpHeaders,Loginrequest));
    // }

    

    
}