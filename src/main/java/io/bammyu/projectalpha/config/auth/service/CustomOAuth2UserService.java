package io.bammyu.projectalpha.config.auth.service;

import io.bammyu.projectalpha.config.auth.dto.OAuthAttributes;
import io.bammyu.projectalpha.config.auth.dto.SessionUser;
import io.bammyu.projectalpha.user.domain.User;
import io.bammyu.projectalpha.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final HttpSession httpSession;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());

        User user = userRepository
                .findByOauthIdAndOauthProvider(attributes.getOauthId(), attributes.getRegistrationId())
                .orElse(attributes.toEntity());

        User savedUser = userRepository.save(user);
        httpSession.setAttribute("user", new SessionUser(savedUser));

        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

        return new DefaultOAuth2User(mappedAuthorities, attributes.getAttributes(), attributes.getNameAttributeKey());
    }
}
