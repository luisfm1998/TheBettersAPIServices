package TheBettersAPIServices.TheBettersAPIServices.Services.AuthenticationServices;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import com.nimbusds.jose.JOSEException;
import TheBettersAPIServices.TheBettersAPIServices.Security.JwtIO;
import TheBettersAPIServices.TheBettersAPIServices.Services.UsersServices.SearchUsers;
import TheBettersAPIServices.TheBettersAPIServices.Utilities.DateUtils;
import TheBettersAPIServices.TheBettersAPIServices.Utilities.Validations;
import TheBettersAPIServices.TheBettersAPIServices.Utilities.Encryption.JWE;
import TheBettersAPIServices.TheBettersAPIServices.dto.JwtResponse;
import TheBettersAPIServices.TheBettersAPIServices.dto.Response;
import TheBettersAPIServices.TheBettersAPIServices.dto.ReturnValidators;
import TheBettersAPIServices.TheBettersAPIServices.Utilities.DataType;
import org.json.JSONObject;

@Service
public class AuthService {
    @Autowired
    private JwtIO jwtIO;

    @Autowired
    private DateUtils dateUtils;

    @Autowired
    private JWE _jwe;

    @Autowired
    private SearchUsers _searchUsers;

    @Value("${jms.jwt.token.expires-in}")
    private int EXPIRES_IN;

    @Value("${controller.localURL}")
    private String localURL;

    @Value("${controller.localusername}")
    private String localusername;

    @Value("${controller.localpassword}")
    private String localpassword;

    ReturnValidators reponseValid = new ReturnValidators();

    public Response login(MultiValueMap<String, String> paramMap) throws SQLException, InvalidKeySpecException, NoSuchAlgorithmException, ParseException, IOException, JOSEException {
        Response _respuesta = new Response();
        Response _responseUsuario = new Response();
        Validations _val = new Validations();
        String BuildTokenJWT = "", TokenJWTSession="";
        reponseValid = _val.ValidationsData(paramMap.getFirst("Email"), "Email", DataType.Type.IsMail, true);
        reponseValid = _val.ValidationsData(paramMap.getFirst("Password"), "Password", DataType.Type.IsPassword, true);
        if (reponseValid.getErrors() == 0) {
            _responseUsuario = _searchUsers.GetUserAuthentication(paramMap);
            if(_responseUsuario.getIsOK()){
                BuildTokenJWT = _jwe.Decryption(_responseUsuario.getMessage());
                JSONObject jsonObject = new JSONObject(BuildTokenJWT);
                String _passwordUserBD = new  String(jsonObject.getString("Password"));
                String _passwordUser = new String(paramMap.getFirst("Password"));
                if(_passwordUserBD.equals(_passwordUser)){                    
                    TokenJWTSession = GenerateTokenAuthentication(paramMap.getFirst("Email"));
                    _respuesta.setAuthorization(TokenJWTSession);
                    _respuesta.setIsOK(true);
                    _respuesta.setMessage("Access granted");
                }else{
                    _respuesta.setAuthorization("Access denied");
                    _respuesta.setMessage("Wrong password or username");
                }
            }else{
                _respuesta.setIsOK(false);
                _respuesta.setMessage("User not exist");          
            }
        } else {
            _respuesta.setMessage(reponseValid.getErrorDesc());
            _respuesta.setIsOK(false);
            _respuesta.setAuthorization("Some data is missing");
        }
        return _respuesta;
    }
    public String GenerateTokenAuthentication(String _Email){
        JwtResponse jwt = JwtResponse.builder()
        .tokenType("bearer")
        .accessToken(jwtIO.generateToken("Users"))
        .issuedAt(dateUtils.getDateMillis() + "")
        .clientId(_Email)
        .expiresIn(EXPIRES_IN)
        .build();
        return jwt.getAccessToken();
    }
    public String Serv() {
        return "HOla";
    }
    public static char StringToChar(String s) {
		return s.charAt(0);
	}
}
