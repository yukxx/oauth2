package yukx.security.auth.enums;

/**
 * @ClassName OauthClientEnum
 * @Description oauth client
 * @Author yukx
 * @Date 2020-09-16 14:34
 **/
public enum OauthClientEnum {
    CLIENT1("client_1", "123456"),
    CLIENT2("client_2", "123456"),
    USER("user", "123456");

    public String clientId;
    public String secret;

    OauthClientEnum(String clientId, String secret) {
        this.clientId = clientId;
        this.secret = secret;
    }}
