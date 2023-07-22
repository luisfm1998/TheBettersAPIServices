package TheBettersAPIServices.TheBettersAPIServices.Services.UsersServices;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;

import TheBettersAPIServices.TheBettersAPIServices.Utilities.Validations;
import TheBettersAPIServices.TheBettersAPIServices.Utilities.Encryption.JWE;
import TheBettersAPIServices.TheBettersAPIServices.dto.Response;
import TheBettersAPIServices.TheBettersAPIServices.dto.ReturnValidators;
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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    ReturnValidators reponseValid = new ReturnValidators();

    public Response CreateUserAuthetication(MultiValueMap<String, String> paramMap) throws NoSuchAlgorithmException,
            InvalidKeySpecException, JOSEException, IOException {
        Response _respuesta = new Response();
        Response _adduser = new Response();
        Validations _val = new Validations();
        String TokenPassword = "";
        reponseValid = _val.ValidationsData(paramMap.getFirst("Email"), "Email", DataType.Type.IsMail, true);
        reponseValid = _val.ValidationsData(paramMap.getFirst("Nombre"), "Nombre", DataType.Type.IsNames, true);
        reponseValid = _val.ValidationsData(paramMap.getFirst("ApellidoP"), "ApellidoP", DataType.Type.IsNames, true);
        reponseValid = _val.ValidationsData(paramMap.getFirst("ApellidoM"), "ApellidoM", DataType.Type.IsNames, true);
        reponseValid = _val.ValidationsData(paramMap.getFirst("Telefono"), "Telefono", DataType.Type.IsPhone, true);
        reponseValid = _val.ValidationsData(paramMap.getFirst("Password"), "Password", DataType.Type.IsPassword, true);
        if (reponseValid.getErrors() == 0) {
            TokenPassword = jwe.Encryption(paramMap);
            _adduser = AddUserAuthentication(paramMap, TokenPassword);
            if (_adduser.getIsOK()) {
                _respuesta.setIsOK(true);
                _respuesta.setAuthorization("Authorized");
                _respuesta.setMessage("User created successfully");
                _respuesta.setData(_adduser.getData());
                _respuesta.setMessage(_adduser.getMessage());
            } else {
                _respuesta.setMessage(_adduser.getMessage());
                _respuesta.setIsOK(false);
            }
            _respuesta = _adduser;
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

    public Response AddUserAuthentication(MultiValueMap<String, String> paramMap, String _Password) {
        Response _respuesta = new Response();
        Validations _val = new Validations();
        String JsonResponse, isok, message;
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("SP_AddUserAuthentication");
            Map<String, Object> inParams = new HashMap<>();
            inParams.put("Email", paramMap.getFirst("Email"));
            inParams.put("Password", _Password);
            inParams.put("Nombre", paramMap.getFirst("Nombre"));
            inParams.put("ApellidoP", paramMap.getFirst("ApellidoP"));
            inParams.put("ApellidoM", paramMap.getFirst("ApellidoM"));
            inParams.put("Telefono", paramMap.getFirst("Telefono"));
            Map<String, Object> result = jdbcCall.execute(inParams);
            JsonResponse = _val.ToJson(result.get("#result-set-1"));
            String dato = result.get("#result-set-1").getClass().getSimpleName();
            Object[] objects = objectMapper.readValue(JsonResponse, Object[].class);
            isok = (String) ((Map<String, Object>) objects[0]).get("Isok");
            message = (String) ((Map<String, Object>) objects[0]).get("Mensaje");
            _respuesta.setIsOK(Boolean.parseBoolean(isok));
            _respuesta.setData(_val.Sql(result));
            _respuesta.setAuthorization("Authorized");
            _respuesta.setMessage(dato);
        } catch (Exception e) {
            _respuesta.setAuthorization(e.getMessage());
        }
        return _respuesta;
    }

    public Response GetUserAuthentication(MultiValueMap<String, String> paramMap) {
        Response _respuesta = new Response();
        Validations _val = new Validations();
        String email, password, isok;
        String JsonResponse;
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("SP_AuthenticationUserLogin");
            Map<String, Object> inParams = new HashMap<>();
            inParams.put("Email", paramMap.getFirst("Email"));
            inParams.put("Password", paramMap.getFirst("Password"));

            Map<String, Object> result = jdbcCall.execute(inParams);
            JsonResponse = _val.ToJson(result.get("#result-set-1"));
            Object[] objects = objectMapper.readValue(JsonResponse, Object[].class);
            password = (String) ((Map<String, Object>) objects[0]).get("password");
            isok = (String) ((Map<String, Object>) objects[0]).get("Isok");
            email = (String) ((Map<String, Object>) objects[0]).get("correo");

            _respuesta.setIsOK(Boolean.parseBoolean(isok));
            _respuesta.setMessage(password);
            _respuesta.setAuthorization(email);
        } catch (Exception e) {
            _respuesta.setAuthorization(e.getMessage());
        }
        return _respuesta;
    }

}
