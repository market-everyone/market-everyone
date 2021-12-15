package web.item.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findByIdGreaterThanEqualOrderById(Long id, Pageable pageable);

    List<Item> findByCategoryId(Long categoryId);
}
