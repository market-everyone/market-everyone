package web.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import web.common.util.ImageUtil;
import web.item.controller.dto.request.ItemRequest;
import web.item.controller.dto.response.ItemResponse;
import web.item.domain.Item;
import web.item.domain.ItemRepository;
import web.item.domain.category.Category;
import web.seller.domain.Seller;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public Long save(ItemRequest itemInsertRequest, Category category, Seller seller, HttpServletResponse res, @RequestPart(value = "itemImage", required = false) MultipartFile file) throws IOException {
        ImageUtil imageUtil = new ImageUtil();

        if (file.getSize() != 0) {
            String fileName = imageUtil.uploadImage(res, file, seller.getId());
            String path = Paths.get("module-web/src/main/resources/static/images").toAbsolutePath() + File.separator + seller.getId() + File.separator;
            itemInsertRequest.setImageName(fileName);
            itemInsertRequest.setImagePath(path);
        }

        itemInsertRequest.setCategory(category);
        itemInsertRequest.setSeller(seller);

        Item item = itemInsertRequest.toItem();
        return itemRepository.save(item).getId();

    }

    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public Long update(ItemRequest itemInsertRequest, Long id) throws IOException {
        Item item = itemRepository.findById(id).orElseThrow(IllegalArgumentException::new);

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

    @Transactional(readOnly = true)
    public List<ItemResponse> findAllBySellerId(Long id, Pageable pageable) {
        return itemRepository.findByCategoryIdOrderByIdDesc(id, pageable)
                .stream()
                .map(ItemResponse::of)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public Page<ItemResponse> findAllBySeller(Long id, Pageable pageable) {
        return itemRepository.findBySellerIdOrderByIdAsc(id, pageable).map(item -> new ItemResponse(item.getId(), item.getName(), item.getImagePath(), item.getPrice()));
    }
}
