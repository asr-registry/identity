package af.asr.identity.infrastructure.security.token;

public class JwtTokenConstants {
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public static final String SIGNING_KEY = "badarhashimi@1235";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}