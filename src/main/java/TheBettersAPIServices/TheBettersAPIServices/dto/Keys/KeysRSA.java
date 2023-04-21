package TheBettersAPIServices.TheBettersAPIServices.dto.Keys;

public class KeysRSA {
    private String PublicKey;
    private String PrivateKey;
    public KeysRSA() {
        // No hace nada
    }

    public String getPublicKey() {
        return this.PublicKey;
    }

    public void setPublicKey(String PublicKey) {
        this.PublicKey = PublicKey;
    }

    public String getPrivateKey() {
        return this.PrivateKey;
    }

    public void setPrivateKey(String PrivateKey) {
        this.PrivateKey = PrivateKey;
    }

    @Override
    public String toString() {
        return "{" +
                " PublicKey='" + getPublicKey() + "'" +
                ", PrivateKey='" + getPrivateKey() + "'" +
                "}";
    }
}
