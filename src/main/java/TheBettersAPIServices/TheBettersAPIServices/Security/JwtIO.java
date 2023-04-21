package TheBettersAPIServices.TheBettersAPIServices.Security;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import TheBettersAPIServices.TheBettersAPIServices.Utilities.GsonUtils;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;

@Component
public class JwtIO {

    @Value("${jms.jwt.token.secret:secret}")
    private String SECRET;
    @Value("${jms.jwt.timezone:UTC}")
    private String TIMEZONE;
    @Value("${jms.jwt.token.expires-in:3600}")
    private int EXPIRES_IN;
    @Value("${jms.jwt.issuer:none}")
    private String ISSUER;
     

    public String generateToken(Object src){
        //Obtiene los datos del objeto a encriptar o ingresar al jwt
        String subject = GsonUtils.serializae(src);

        //Se asigana la llave privata o frase para encriptar el token
        Signer signer = HMACSigner.newSHA256Signer(SECRET);

        //Se obtiene la zona horaria
        TimeZone tz = TimeZone.getTimeZone(TIMEZONE);
        ZonedDateTime zdt=  ZonedDateTime.now(tz.toZoneId()).plusSeconds(EXPIRES_IN);

        //Creación de token
        JWT jwt = new JWT()
        .setIssuer(ISSUER)
        .setIssuedAt(ZonedDateTime.now(tz.toZoneId()))
        .setSubject(subject)
        .setExpiration(zdt);
        
        return JWT.getEncoder().encode(jwt, signer);
    }

    public boolean validateToken(String encodedJWT){
        JWT jwt = jwt(encodedJWT);
        return jwt.isExpired();
    }
    //obtener payload
    public String getPayload(String encodedJWT){
        JWT jwt = jwt(encodedJWT);
        return jwt.subject;
    }
    //Codificar el string que se envía
    private JWT jwt(String encodedJWT){
        Verifier verifier = HMACVerifier.newVerifier(SECRET);
        return JWT.getDecoder().decode(encodedJWT,verifier);
    }
    
}
