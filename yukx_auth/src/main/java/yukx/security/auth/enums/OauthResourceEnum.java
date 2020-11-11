package yukx.security.auth.enums;

/**
 * @ClassName OauthClientEnum
 * @Description oauth client
 * @Author yukx
 * @Date 2020-09-16 14:34
 **/
public enum OauthResourceEnum {
    RESOURCE1("resource1", "123456"),
    USER("user", "123456");

    public String resource;
    public String secret;

    OauthResourceEnum(String resource, String secret) {
        this.resource = resource;
        this.secret = secret;
    }}
