package web.common.auth;

import org.springframework.security.core.Authentication;
import web.security.SellerPrincipal;
import web.seller.domain.Seller;

public class SellerAuthConverter {

    public static Seller findCurrentUserFromAuth(Authentication authentication) {
        SellerPrincipal principal = (SellerPrincipal) authentication.getPrincipal();

        return principal.getSeller();
    }
}
