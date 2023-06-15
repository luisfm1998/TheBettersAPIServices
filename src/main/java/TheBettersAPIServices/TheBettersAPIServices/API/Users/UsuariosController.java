package TheBettersAPIServices.TheBettersAPIServices.API.Users;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nimbusds.jose.JOSEException;
import TheBettersAPIServices.TheBettersAPIServices.Services.UsersServices.SearchUsers;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "API")
public class UsuariosController {

    @Autowired
    private SearchUsers _searchUsers;

    @PostMapping(value = "/oauth/clien_credential/autheticationcreateuser")
    public ResponseEntity<Object> CreateUserAuthetication(@RequestParam MultiValueMap<String, String> paramMap) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, JOSEException, ParseException, IOException{
        return ResponseEntity.ok(_searchUsers.CreateUserAuthetication(paramMap));
    }
}
