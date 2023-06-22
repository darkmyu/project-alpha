package io.bammyu.projectalpha.user.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import io.bammyu.projectalpha.common.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String email;

    private String username;

    private String profileImageUrl;

    private String oauthId;

    private String oauthProvider;

    @Builder
    public User(String email, String username, String profileImageUrl, String oauthId, String oauthProvider) {
        this.email = email;
        this.username = username;
        this.profileImageUrl = profileImageUrl;
        this.oauthId = oauthId;
        this.oauthProvider = oauthProvider;
    }
}
