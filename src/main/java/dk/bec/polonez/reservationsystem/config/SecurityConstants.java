package dk.bec.polonez.reservationsystem.config;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTH_HEADER = "Authorization";
    public static final String LOGIN_URL = "/api/user/login";
    public static final String SIGN_UP_URL = "/api/user/signup";
}