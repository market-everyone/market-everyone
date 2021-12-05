package web.security.oauth2;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import web.exception.auth.UnsupportedOauthVendorException;
import web.security.UserPrincipal;
import web.security.oauth2.provider.GoogleUserInfo;
import web.security.oauth2.provider.OAuth2UserInfo;
import web.user.domain.User;
import web.user.domain.UserRepository;

import java.util.Optional;

@Service
public class Oauth2UserPrincipalService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    public Oauth2UserPrincipalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else {
            throw new UnsupportedOauthVendorException();
        }

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();

        Optional<User> optionalUser = userRepository.findByProviderAndProviderId(provider, providerId);

        User user = optionalUser
                .map(value -> updateExistingUser(value, oAuth2UserInfo))
                .orElseGet(() -> registerNewUser(oAuth2UserInfo));

        return new UserPrincipal(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserInfo oAuth2UserInfo) {
        User user = User.builder()
//                .accountId(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .provider(oAuth2UserInfo.getProvider())
                .providerId(oAuth2UserInfo.getProviderId())
                .build();
        return userRepository.save(user);
    }

    private User updateExistingUser(User user, OAuth2UserInfo oAuth2UserInfo) {
        user.setEmail(oAuth2UserInfo.getEmail());
//        user.setAccountId(oAuth2UserInfo.getName());
        return userRepository.save(user);
    }
}
