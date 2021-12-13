package web.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.item.controller.dto.request.ItemRequest;
import web.item.domain.Item;
import web.item.domain.ItemRepository;
import web.item.domain.category.Category;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;


    public Long save(ItemRequest itemInsertRequest, Category category, String imagePath) {
        itemInsertRequest.setCategory(category);
        itemInsertRequest.setImagePath(imagePath);
        Item item = itemInsertRequest.toItem();
        return itemRepository.save(item).getId();

    }

    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<Item> itemList() {
        return itemRepository.findAll();
    }

    public Long update(ItemRequest itemInsertRequest, Category category, String imagePath, String itemId) {
        Item item = itemRepository.findById(Long.valueOf(itemId)).orElseThrow(IllegalArgumentException::new);

        item.update(itemInsertRequest);
        return itemRepository.save(item).getId();
    }

    @Transactional
    public void delete(Item item) {
        itemRepository.delete(item);
    }
}
