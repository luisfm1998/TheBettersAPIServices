package TheBettersAPIServices.TheBettersAPIServices.Utilities.Encryption;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;

import TheBettersAPIServices.TheBettersAPIServices.dto.Response;
import TheBettersAPIServices.TheBettersAPIServices.dto.Keys.KeysRSA;

@Service
public class JWE {
    public String EncryptionJWE(String Password) throws NoSuchAlgorithmException, InvalidKeySpecException, JOSEException, ParseException, IOException {
        GenerateDecode gs = new GenerateDecode();
        String[] WeeCompany = { "Luis Angel", "TheBetter", "System", "Emisión" ,"Que show","Ya jaloo","Barbaro","GPI"};

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        // Inicializa el tamaño de la clave
        keyPairGenerator.initialize(2048);


        // Generar el par de claves
        KeyPair keyPair = keyPairGenerator.genKeyPair();


        // Crear KeyFactory y especificaciones de claves RSA
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);
        RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(keyPair.getPrivate(), RSAPrivateKeySpec.class);


        // Generate (and retrieve) RSA Keys from the KeyFactory using Keys Specs
		RSAPublicKey publicRsaKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);


		RSAPrivateKey privateRsaKey = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);


        // Guardando llaves privadas y publicas
        var pub = keyPair.getPublic();
        var pvt = keyPair.getPrivate();

        Base64.Encoder encoder = Base64.getEncoder();
        Base64.Decoder decoder = Base64.getDecoder();


        // Codificación de PrivateKey a Base64
        String EncoderPublicKey = encoder.encodeToString(pub.getEncoded());
        String EncoderPrivateKey = encoder.encodeToString(pvt.getEncoded());


        //Se guardaran decodifican las llaves
        //Private key
        PrivateKey LlavePrivaWee = gs.getKeyPrivate();
        // Crea un nuevo RSAPrivateKeySpec con parámetros de clave adicionales
        // (LlavePrivaWee).
        RSAPrivateKeySpec PrivadaWee = keyFactory.getKeySpec(LlavePrivaWee, RSAPrivateKeySpec.class);
        // Generar un objeto de clave privada con base en la clave proporcionada
        // (PrivadaWee).
        RSAPrivateKey PrivateKeyToken = (RSAPrivateKey) keyFactory.generatePrivate(PrivadaWee);



        //Public key
        PublicKey LlavePublicaWee = gs.getKeyPublic();
        // Crea un nuevo RSAPrivateKeySpec con parámetros de clave adicionales
        // (LlavePublicaWee).
        RSAPublicKeySpec PublicWee = keyFactory.getKeySpec(LlavePublicaWee, RSAPublicKeySpec.class);
        // Generar un objeto de clave privada con base en la clave proporcionada
        // (PublicWee).
		RSAPublicKey PublicKeyfinal = (RSAPublicKey) keyFactory.generatePublic(PublicWee);




        // RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);




        JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder();
		claimsSet.issuer("test-user");
		claimsSet.subject("JWE-Authentication-Example");


        claimsSet.claim("appId", "Holis");
		claimsSet.claim("userId", "4431d8dc-2f69-4057-9b83-a59385d18c03");
		claimsSet.claim("role", "Admin");
		claimsSet.claim("applicationType", "WEB");
		claimsSet.claim("clientRemoteAddress", "192.168.1.2");
		
		claimsSet.expirationTime(new Date(new Date().getTime() + 1000 * 60 * 10));
		claimsSet.notBeforeTime(new Date());
		claimsSet.jwtID(UUID.randomUUID().toString());


        // Create the JWE header and specify:
		// RSA-OAEP as the encryption algorithm
		// 128-bit AES/GCM as the encryption method
		JWEHeader header = new JWEHeader(JWEAlgorithm.RSA1_5, EncryptionMethod.A256CBC_HS512);

		// Initialized the EncryptedJWT object
		EncryptedJWT jwt = new EncryptedJWT(header, claimsSet.build());

		// Create an RSA encrypted with the specified public RSA key
		RSAEncrypter encrypter = new RSAEncrypter(PublicKeyfinal);

        jwt.encrypt(encrypter);


		// Token JWE
		String jwtString = jwt.serialize();


		jwt = EncryptedJWT.parse(jwtString);



        RSADecrypter decrypter = new RSADecrypter(PrivateKeyToken);


        jwt.decrypt(decrypter);

        String val = jwt.getJWTClaimsSet().toString();


        return Decryption(jwtString);
    }   
    public String Decryption(String Token) throws ParseException, InvalidKeySpecException, NoSuchAlgorithmException, IOException, JOSEException{
        EncryptedJWT jwt;
        GenerateDecode gs = new GenerateDecode();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        //Se guardaran decodifican las llaves
        //Private key
        PrivateKey LlavePrivaWee = gs.getKeyPrivate();
        // Crea un nuevo RSAPrivateKeySpec con parámetros de clave adicionales
        // (LlavePrivaWee).
        RSAPrivateKeySpec PrivadaWee = keyFactory.getKeySpec(LlavePrivaWee, RSAPrivateKeySpec.class);
        // Generar un objeto de clave privada con base en la clave proporcionada
        // (PrivadaWee).
        RSAPrivateKey PrivateKeyToken = (RSAPrivateKey) keyFactory.generatePrivate(PrivadaWee);

		// In order to read back the data from the token using your private RSA key:
		// parse the JWT text string using EncryptedJWT object
		jwt = EncryptedJWT.parse(Token);

        RSADecrypter decrypter = new RSADecrypter(PrivateKeyToken);

        jwt.decrypt(decrypter);

        String val = jwt.getJWTClaimsSet().toString();

        return val;
    }
    public String Encryption(String Email,String _Name,String _AP, String _AM, String _Telefono, String Password) throws JOSEException, InvalidKeySpecException, NoSuchAlgorithmException, IOException{
        
        String[] User = { Email, _Name, _AP, _AM ,_Telefono};     
        GenerateDecode _decode = new GenerateDecode();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        //Obtención de Public key
        PublicKey LlavePublicaWee = _decode.getKeyPublic();
        // Crea un nuevo RSAPrivateKeySpec con parámetros de clave adicionales
        RSAPublicKeySpec PublicWee = keyFactory.getKeySpec(LlavePublicaWee, RSAPublicKeySpec.class);
        // Generar un objeto de clave privada con base en la clave proporcionada
		RSAPublicKey PublicKeyfinal = (RSAPublicKey) keyFactory.generatePublic(PublicWee);
             
       
        JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder();
		claimsSet.issuer("The-Better");
		claimsSet.subject("AuthenticationPassword");
        claimsSet.claim("User", User);
        claimsSet.claim("Password", Password);
		claimsSet.jwtID(UUID.randomUUID().toString());

        // Crea el encabezado JWE y especifica: RSA-OAEP como algoritmo de cifrado, A256CBC_HS512 como método de cifrado
        JWEHeader header = new JWEHeader(JWEAlgorithm.RSA1_5, EncryptionMethod.A256CBC_HS512);

        // Inicializó el objeto EncryptedJWT
        EncryptedJWT jwt = new EncryptedJWT(header, claimsSet.build());

        // Crear un RSA cifrado con la clave RSA pública especificada
        RSAEncrypter encrypter = new RSAEncrypter(PublicKeyfinal);

        // Haciendo el cifrado real
        jwt.encrypt(encrypter);

        // Token JWE
		String jwtString = jwt.serialize();

        return jwtString;
    }
    public Response GenerateRSAKeys() throws NoSuchAlgorithmException, InvalidKeySpecException{
        Response _respuesta = new Response();
        KeysRSA _Key = new KeysRSA();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        // Inicializa el tamaño de la clave
        keyPairGenerator.initialize(2048);

        // Generar el par de claves
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        try {

            // Guardando llaves privadas y publicas
            var pub = keyPair.getPublic();
            var pvt = keyPair.getPrivate();
    
            Base64.Encoder encoder = Base64.getEncoder();
    
            // Codificación de PrivateKey a Base64
            String EncoderPublicKey = encoder.encodeToString(pub.getEncoded());
            String EncoderPrivateKey = encoder.encodeToString(pvt.getEncoded());
    
            _Key.setPublicKey("-----BEGIN PUBLIC KEY-----"+EncoderPublicKey+"-----END PUBLIC KEY-----");
            _Key.setPrivateKey("-----BEGIN PRIVATE KEY-----"+EncoderPrivateKey+"-----END PRIVATE KEY-----");
    
            _respuesta.setAuthorization("Authorized");
            _respuesta.setIsOK(true);
            _respuesta.setMessage("Succes");
            _respuesta.setData(_Key); 
        } catch (Exception e) {
            _respuesta.setMessage(e.getMessage());
        }
        return _respuesta;
    }
}

