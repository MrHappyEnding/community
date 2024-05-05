package life.serena.community.community.dto;

public class AccessTokeDTO {
    private String cilent_id;
    private String cilent_secret;
    private String code;
    private String redirect_uri;
    private String state;

    public String getCilent_id() {
        return cilent_id;
    }

    public void setCilent_id(String cilent_id) {
        this.cilent_id = cilent_id;
    }

    public String getCilent_secret() {
        return cilent_secret;
    }

    public void setCilent_secret(String cilent_secret) {
        this.cilent_secret = cilent_secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
