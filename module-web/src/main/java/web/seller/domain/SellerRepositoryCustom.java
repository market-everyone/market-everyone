package web.seller.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SellerRepositoryCustom {
    Page<Seller> findApprovalSellers(SellerStatus status, Pageable pageable);
}
