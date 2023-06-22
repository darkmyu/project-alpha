package io.bammyu.projectalpha.config.auth.dto;

import io.bammyu.projectalpha.user.domain.User;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String registrationId;
    private String nameAttributeKey;
    private String oauthId;
    private String email;
    private String username;
    private String picture;

    public User toEntity() {
        return User.builder()
                .email(email)
                .username(username)
                .profileImageUrl(picture)
                .oauthId(oauthId)
                .oauthProvider(registrationId)
                .build();
    }

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String registrationId, String nameAttributeKey,
            String oauthId, String email, String username, String picture) {
        this.attributes = attributes;
        this.registrationId = registrationId;
        this.nameAttributeKey = nameAttributeKey;
        this.oauthId = oauthId;
        this.email = email;
        this.username = username;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
            Map<String, Object> attributes) {
        if (registrationId.equals("naver")) {
            return ofNaver(registrationId, userNameAttributeName, attributes);
        }
        return ofGoogle(registrationId, userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String registrationId, String userNameAttributeName,
            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .oauthId(attributes.get(userNameAttributeName).toString())
                .username(attributes.get("name").toString())
                .email(attributes.get("email").toString())
                .picture(attributes.get("picture").toString())
                .attributes(attributes)
                .registrationId(registrationId)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String registrationId, String userNameAttributeName,
            Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .oauthId(response.get(userNameAttributeName).toString())
                .username(response.get("name").toString())
                .email(response.get("email").toString())
                .picture(response.get("profile_image").toString())
                .attributes(response)
                .registrationId(registrationId)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
}
