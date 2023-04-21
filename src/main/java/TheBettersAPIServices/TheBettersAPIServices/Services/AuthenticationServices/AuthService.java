package TheBettersAPIServices.TheBettersAPIServices.Services.AuthenticationServices;

import org.springframework.http.HttpHeaders;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import TheBettersAPIServices.TheBettersAPIServices.Security.JwtIO;
import TheBettersAPIServices.TheBettersAPIServices.Services.UsersServices.SearchUsers;
import TheBettersAPIServices.TheBettersAPIServices.Utilities.DateUtils;
import TheBettersAPIServices.TheBettersAPIServices.Utilities.Validations;
import TheBettersAPIServices.TheBettersAPIServices.dto.JwtResponse;
import TheBettersAPIServices.TheBettersAPIServices.dto.Request;
import TheBettersAPIServices.TheBettersAPIServices.dto.Response;
import TheBettersAPIServices.TheBettersAPIServices.dto.ReturnValidators;
import TheBettersAPIServices.TheBettersAPIServices.dto.Users.SearchUsersResponse;
import TheBettersAPIServices.TheBettersAPIServices.Utilities.DataType;

@Service
public class AuthService {
    @Autowired
    private JwtIO jwtIO;

    @Autowired
    private DateUtils dateUtils;

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

    public Response login(MultiValueMap<String, String> paramMap) throws SQLException {
        Response _respuesta = new Response();
        Validations _val = new Validations();
        SearchUsersResponse _user = new SearchUsersResponse();
        reponseValid = _val.ValidationsData(paramMap.getFirst("Email"), "Email", DataType.Type.IsString, true);
        if (reponseValid.getErrors() == 0) {
            _user = _searchUsers.GetUserAuthentication(paramMap.getFirst("Email"));
            if(_user.getIsExist()){
                _respuesta.setAuthorization(_user.getPassword());
            }else{
                _respuesta.setIsOK(false);
                _respuesta.setMessage("User not exist");
            }
        } else {
            _respuesta.setMessage(reponseValid.getErrorDesc());
            _respuesta.setIsOK(false);
            _respuesta.setAuthorization("Some data is missing");
        }
        // JwtResponse jwt = JwtResponse.builder()
        // .tokenType("bearer")
        // .accessToken(jwtIO.generateToken("Users"))
        // .issuedAt(dateUtils.getDateMillis() + "")
        // .clientId(paramMap.getFirst("Email"))
        // .expiresIn(EXPIRES_IN)
        // .build();
        return _respuesta;

    }

    public String Serv() {
        return "HOla";
    }
}
