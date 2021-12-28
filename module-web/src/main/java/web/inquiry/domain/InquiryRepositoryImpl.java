package web.inquiry.domain;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import web.common.util.QueryDslUtil;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;
import static web.inquiry.domain.QInquiry.inquiry;

@RequiredArgsConstructor
public class InquiryRepositoryImpl implements InquiryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Inquiry> findPageInquiry(Pageable pageable) {
        OrderSpecifier[] orderSpecifiers = getAllOrderSpecifiers(pageable).stream()
                .toArray(OrderSpecifier[]::new);
        QueryResults<Inquiry> result = queryFactory.selectFrom(inquiry)
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Inquiry> content = result.getResults();
        long total = result.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier> ORDERS = new ArrayList<>();
        if (!isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "INQUIRY_NO":
                        OrderSpecifier<?> inquiryNo = QueryDslUtil
                                .getSortedColumn(direction, inquiry, "id");
                        ORDERS.add(inquiryNo);
                        break;
                    default:
                        break;
                }
            }
        }
        return ORDERS;
    }
}
