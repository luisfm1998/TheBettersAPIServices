package TheBettersAPIServices.TheBettersAPIServices.API.AuthenticationController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;


import TheBettersAPIServices.TheBettersAPIServices.Exceptions.Apiunauthorized;
import TheBettersAPIServices.TheBettersAPIServices.Utilities.Encryption.JWE;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "API")
public class AuthController {

    @Autowired
    private TheBettersAPIServices.TheBettersAPIServices.Services.AuthenticationServices.AuthService authService;

    @Autowired
    private JWE _jwe;


    @PostMapping(value = "/oauth/clien_credential/accesstoken")
    public ResponseEntity<Object> login(@RequestParam MultiValueMap<String, String> paramMap) throws Apiunauthorized, SQLException, InvalidKeySpecException, NoSuchAlgorithmException, ParseException, IOException, JOSEException{
        return ResponseEntity.ok(authService.login(paramMap));
    }

    @PostMapping(value = "/oauth/clien_credential/rsageneratekeys", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> GenerateRSAKeys(@RequestBody MultiValueMap<String, String> paramMap) throws NoSuchAlgorithmException, InvalidKeySpecException{
        return ResponseEntity.ok(_jwe.GenerateRSAKeys());
    }
    
}