package web.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.item.controller.dto.request.ItemRequest;
import web.item.controller.dto.response.ItemResponse;
import web.item.domain.Item;
import web.item.domain.ItemRepository;
import web.item.domain.category.Category;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<ItemResponse> findPagesBy(Long from, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        return itemRepository.findByIdGreaterThanEqualOrderById(from, pageRequest)
                .stream()
                .map(ItemResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> findByCategoryId(Long categoryId) {
        return itemRepository.findByCategoryId(categoryId)
                .stream()
                .map(ItemResponse::of)
                .collect(Collectors.toList());
    }
}
