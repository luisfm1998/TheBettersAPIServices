package TheBettersAPIServices.TheBettersAPIServices.Services.UsersServices;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.nimbusds.jose.JOSEException;

import TheBettersAPIServices.TheBettersAPIServices.Utilities.Validations;
import TheBettersAPIServices.TheBettersAPIServices.Utilities.Encryption.JWE;
import TheBettersAPIServices.TheBettersAPIServices.dto.Response;
import TheBettersAPIServices.TheBettersAPIServices.dto.ReturnValidators;
import TheBettersAPIServices.TheBettersAPIServices.dto.Users.SearchUsersResponse;
import TheBettersAPIServices.TheBettersAPIServices.Utilities.DataType;

@Service
public class SearchUsers {

    @Autowired
    private JWE jwe;

    @Value("${controller.localURL}")
    private String localURL;

    @Value("${controller.localusername}")
    private String localusername;

    @Value("${controller.localpassword}")
    private String localpassword;

    ReturnValidators reponseValid = new ReturnValidators();

    public SearchUsersResponse GetUserAuthentication(String _email) throws SQLException {
        SearchUsersResponse _user = new SearchUsersResponse();
        Connection conn = DriverManager.getConnection(localURL, localusername, localpassword);
        String QueryPersons = "SELECT * FROM USUARIOS us where us.EMAIL = '" + _email + "'";
        try (PreparedStatement preparedStatement = conn.prepareStatement(QueryPersons);) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    _user.setNombre(resultSet.getString("NOMBRE"));
                    _user.setPassword(resultSet.getString("PASSWORD"));
                    _user.setidUser(resultSet.getString("IDPERSONA"));
                    _user.setEmail(resultSet.getString("EMAIL"));
                    _user.setIsExist(true);
                }
            }
        } catch (Exception e) {
            _user.setIsExist(false);
        }
        return _user;
    }

    public Response CreateUserAuthetication(MultiValueMap<String, String> paramMap) throws NoSuchAlgorithmException,
            InvalidKeySpecException, SQLException, JOSEException, ParseException, IOException {
        Response _respuesta = new Response();
        Response _adduser = new Response();
        Validations _val = new Validations();
        boolean _UserExist = true;
        String TokenPassword = "";
        reponseValid = _val.ValidationsData(paramMap.getFirst("Email"), "Email", DataType.Type.IsMail, true);
        reponseValid = _val.ValidationsData(paramMap.getFirst("Nombre"), "Nombre", DataType.Type.IsString, true);
        reponseValid = _val.ValidationsData(paramMap.getFirst("ApellidoP"), "ApellidoP", DataType.Type.IsString, true);
        reponseValid = _val.ValidationsData(paramMap.getFirst("ApellidoM"), "ApellidoM", DataType.Type.IsString, true);
        reponseValid = _val.ValidationsData(paramMap.getFirst("Telefono"), "Telefono", DataType.Type.IsPhone, true);
        reponseValid = _val.ValidationsData(paramMap.getFirst("Password"), "Password", DataType.Type.IsString, true);
        if (reponseValid.getErrors() == 0) {
            _UserExist = SearchUserExist(paramMap.getFirst("Email"));
            if (!_UserExist) {
                TokenPassword = jwe.Encryption(paramMap.getFirst("Email"), paramMap.getFirst("Nombre"),
                        paramMap.getFirst("ApellidoP"),
                        paramMap.getFirst("ApellidoM"), paramMap.getFirst("Telefono"), paramMap.getFirst("Password"));
                _adduser = AddUserAuthentication(paramMap.getFirst("Nombre"), paramMap.getFirst("ApellidoP"),
                        paramMap.getFirst("ApellidoM"), paramMap.getFirst("Telefono"), paramMap.getFirst("Email"),
                        TokenPassword);
                if (_adduser.getIsOK()) {
                    _respuesta.setIsOK(true);
                    _respuesta.setAuthorization("Authorized");
                    _respuesta.setMessage("User created successfully");
                } else {
                    _respuesta.setMessage("Can't create user");
                }
            } else {
                _respuesta.setIsOK(false);
                _respuesta.setMessage("User exist");
            }
        } else {
            _respuesta.setMessage(reponseValid.getErrorDesc());
            _respuesta.setIsOK(false);
            _respuesta.setAuthorization("Some data is missing");
        }
        return _respuesta;
    }

    public boolean SearchUserExist(String Email) throws SQLException {
        boolean _Exist = false;
        String QueryPersons = "SELECT * FROM USUARIOS us where us.EMAIL = '" + Email + "'";
        Connection conn = DriverManager.getConnection(localURL, localusername, localpassword);
        try (PreparedStatement preparedStatement = conn.prepareStatement(QueryPersons);) {
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    _Exist = true;
                }
            }
        } catch (Exception e) {
            _Exist = false;
        }
        return _Exist;
    }

    public Response AddUserAuthentication(String Nombre, String ApellidoP, String ApellidoM, String Telefono, String Email, String Password) throws SQLException {
        Response _respuesta = new Response();
        String Query = "INSERT INTO USUARIOS(idusuario, Nombre,ApellidoP,ApellidoM,Telefono,Email,Password) values (sys_guid(),'"+ Nombre + "','" + ApellidoP + "','" + ApellidoM + "'," + Telefono + ",'" + Email + "','" + Password + "')";
        //String Query = "INSERT INTO USUARIOS(idusuario, Nombre,ApellidoP,ApellidoM,Telefono,Email,Password) values (sys_guid(),'Diana','Martinez','Carrilo','39383212','Martineas@demo.com','sdlsd23123asd')";
        Connection conn = DriverManager.getConnection(localURL, localusername, localpassword);
        try (PreparedStatement preparedStatement = conn.prepareStatement(Query);) {
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    _respuesta.setIsOK(true);
                    _respuesta.setMessage("Is ok insert");
                }
            }
        } catch (Exception e) {
            _respuesta.setMessage(e.getMessage());
        }
        return _respuesta;
    }

}
