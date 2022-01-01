package web.seller.domain;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static web.seller.domain.QSeller.seller;

@RequiredArgsConstructor
@Transactional
public class SellerRepositoryImpl implements SellerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Seller> findApprovalSellers(SellerStatus status, Pageable pageable) {
        QueryResults<Seller> result = queryFactory.selectFrom(seller)
                .where(seller.sellerStatus.eq(status))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Seller> content = result.getResults();
        long total = result.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
