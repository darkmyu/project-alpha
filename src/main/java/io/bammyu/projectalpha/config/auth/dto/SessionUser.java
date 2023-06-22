package io.bammyu.projectalpha.config.auth.dto;

import io.bammyu.projectalpha.user.domain.User;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {

    private String username;
    private String email;
    private String profileImageUrl;

    public SessionUser(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.profileImageUrl = user.getProfileImageUrl();
    }
}
