package web.item.domain.option;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<ItemOption, Long> {

    void deleteAllByItemId(Long item_id);

    List<ItemOption> findAllByItemId(Long item_id);
}
