package back.global.config.security.oAuth;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuth2Attributes {

    GOOGLE("google", (attributes) -> {
        return new OAuth2UserProfile(
                String.valueOf(attributes.get("sub")),
                (String) attributes.get("name"),
                (String) attributes.get("email")
        );
    });
    private final String registrationId;
    private final Function<Map<String, Object>, OAuth2UserProfile> of;

    OAuth2Attributes(String registrationId, Function<Map<String, Object>, OAuth2UserProfile> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static OAuth2UserProfile extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}
