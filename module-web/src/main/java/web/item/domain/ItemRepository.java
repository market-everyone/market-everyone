package web.item.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findByIdGreaterThanEqualOrderById(Long id, Pageable pageable);

    List<Item> findByCategoryId(Long categoryId);

    List<Item> findByCategoryIdOrderByIdDesc(Long categoryId, Pageable pageable);

    // Seller와 연동이되면 SELLER Id로 변경
    Page<Item> findByCategoryIdOrderByIdAsc(Long categoryId, Pageable pageable);


}
