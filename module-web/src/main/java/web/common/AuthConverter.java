package web.common;

import org.springframework.security.core.Authentication;
import web.security.UserPrincipal;
import web.user.domain.User;

public class AuthConverter {

    public static User findCurrentUserFromAuth(Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return principal.getUser();
    }
}
