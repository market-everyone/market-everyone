package web.seller.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long>, SellerRepositoryCustom {
    Optional<Seller> findByEmail(String email);
}
