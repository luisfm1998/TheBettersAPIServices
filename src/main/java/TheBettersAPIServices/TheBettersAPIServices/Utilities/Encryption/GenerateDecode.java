package TheBettersAPIServices.TheBettersAPIServices.Utilities.Encryption;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class GenerateDecode {

    private final String RutaLLavePriv = "KEYS/PrivateKey.key";
    private final String pathPublicKey = "KEYS/PublicKey.key";


    public PrivateKey getLLavePrivadaString() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        KeyFactory KF = KeyFactory.getInstance("RSA");
        InputStream ios = getFileIOStream(RutaLLavePriv);
        String privateTMPKeyContent = new String(ios.readAllBytes());
        String privateKeyContent = privateTMPKeyContent;
        privateKeyContent = privateKeyContent
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("\n", "");

        byte[] privatekeyDecode = Base64.getDecoder().decode(privateKeyContent);
        PKCS8EncodedKeySpec privateKeySpect = new PKCS8EncodedKeySpec(privatekeyDecode);
        return KF.generatePrivate(privateKeySpect);
    }

    public PrivateKey getLLavePrivadaIO() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        InputStream ios = getFileIOStream(RutaLLavePriv);
        String privateKeyContent = new String(ios.readAllBytes());
        privateKeyContent = privateKeyContent
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("END PRIVATE KEY", "")
                .replace("\n", "");

        byte [] pkcs8EncodedBytes = Base64.getDecoder().decode(privateKeyContent);
        PKCS8EncodedKeySpec privateKeySpect = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory KF = KeyFactory.getInstance("RSA");
        PrivateKey privKey = KF.generatePrivate(privateKeySpect);
        return privKey;
    }
    private InputStream getFileIOStream(final String FileName) {
        InputStream ios = this.getClass().getClassLoader().getResourceAsStream(FileName);
        if (ios == null) {
            throw new IllegalArgumentException(FileName + " is not found");
        }
        return ios;
    }   
    public PrivateKey getKeyPrivate () throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        String PrivateKeyToBase64 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDAC3ynpLdvRF3XfbotOKb0eLJaLCoyyXGEFEBbIiOm0RkWvQ+1xs/WUWCSDDLNyj+3uzEJUoDQs4RZJtjMA8RZjXOjlCl8qKeAquLI8b3iPfefbi/rMjPpz+KI2b8nBLsU2BAt2N5XAvYHWwh4tMqnUk4IAc4B0+0XAf/AzVI3/1ceQSsHyksKhfvmTO6zdRhNMqNiUFiJNu7Tz70DHLe6HT0XsX8oNBnI0qX62k0R+LhtkfD1edR3dp3DEVxNLVT8pkA2AeIaZ2YqEKvOqL3AQ7MawKK06uLeeuqs0yJzCjcpnS6OEiAAlA6ytvAXfaHzJpDPX3BZTBZyFDuNKz9RAgMBAAECggEAFJTGkbLR489IXVFAlBsXrWiH+Kj2BsZW74g6E2o3tud+0FSHq/v4ByhmeJBtG2fa50Zc7XpVishKXUGQB2E5+Dz0ESpOPelXNi9aKZ3aszXf5UqqQQL61EpupSrs1PIpWIUFStyMi3eqRUSIxmLduwvTiAElgkWjiY0xywhWEOERMmKSWMR27SfXbTvWrLj93pfZBNnFtnDRMM9H9NlKbrCD7Y5xvTjc5WTxlETKR4fVzc1kzIL1UX/PzTnUtLTbfX4yXvIQq8exFIZ5lDhz9vTnxHKP/KDDFiq2grzSD/1K5Th3IQ4SxrtyQofxPdpm+P2q54uU/7Wvxjg738pgbwKBgQDGMHy45qyguEPc9+1FVgXK2ZRmqBzPaIJ/EAmqAZ7LB9a6Dn1QZzubaTq2wIp56Z/dBMEg09HEri5z1H/TXwBeTm3q2r548sglu0/2kFhN85gxMiHRktlEoU4lnpGU24jfieflY3ikmc1xvZ+7Ye6A9edOmDdh705F14lDMi4kfwKBgQD4ECpd/0L2wCkhyibglUTIBtif4TpGw7Q2jaXcnny60QpUhjqRlFEdgCqklzBUwj/ElloA7AfRzmFI0jTPmo8mc9cOSzpip32KmTttnSdMFSwAHnGRW8bfGkg+8XMA58V4Q3XsscN1k6Lyr7iAVItvhcxkq5fvYp866SnGeVd0LwKBgD8WpD0/wOgQgBnRGkieD435IRJkpXcor5G/CzSA+4lp32KuxhlYI1Nfdf9C31HbmrYCk2/dvcK8J7Fbja09aqKamMbQBx39OtU4AUZdwJ5f0qymZrmN43fDWVsEVRdrovB1t8n/liHfi9SPJk/B5uhKXu1X6xlT0jjNWbZ4PNQFAoGBAJuqWUvfMYBymd0wZDEuupjjYvEBf3aEvQQ240yM0CzTnS3phbtxXW2rzha3IYcu1qFg0F4fv9BzOC1ga++TSB2TN/MqVhsui+N7wQVKRyzBZHk2oYHcG/JJJekJz2dUCmErxEaiTsvrt6efiaJOa6myRs8ttwxr0QxA4QxBfRXVAoGBAKOfKtFL8vH4e6BcDNfDbpiLUBaE5HKD335joAiNtkwLNkgW3fIoxkfjNr5UbGociU8mk+N0o0ElCpkDsfz70KkQZv7hihj8v70qdWnpWpNrL5GC9ChbRznaTug3D4flOrBRTD/wCJNiEYjtqlNt8XVRF/JEpuOlgGpnZ0qei3lZ";
        byte [] pkcs8EncodedBytes = Base64.getDecoder().decode(PrivateKeyToBase64);
        PKCS8EncodedKeySpec privateKeySpect = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory KF = KeyFactory.getInstance("RSA");
        PrivateKey privKey = KF.generatePrivate(privateKeySpect);
        return privKey;
       
    }
    public PublicKey getKeyPublic () throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        String PublicteKeyToBase64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwAt8p6S3b0Rd1326LTim9HiyWiwqMslxhBRAWyIjptEZFr0PtcbP1lFgkgwyzco/t7sxCVKA0LOEWSbYzAPEWY1zo5QpfKingKriyPG94j33n24v6zIz6c/iiNm/JwS7FNgQLdjeVwL2B1sIeLTKp1JOCAHOAdPtFwH/wM1SN/9XHkErB8pLCoX75kzus3UYTTKjYlBYiTbu08+9Axy3uh09F7F/KDQZyNKl+tpNEfi4bZHw9XnUd3adwxFcTS1U/KZANgHiGmdmKhCrzqi9wEOzGsCitOri3nrqrNMicwo3KZ0ujhIgAJQOsrbwF32h8yaQz19wWUwWchQ7jSs/UQIDAQAB";
        byte [] X509EncodedBytes = Base64.getDecoder().decode(PublicteKeyToBase64);
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(X509EncodedBytes);
        KeyFactory KF = KeyFactory.getInstance("RSA");
        PublicKey publicKey = KF.generatePublic(pubKeySpec);
        return publicKey;
    }
}

