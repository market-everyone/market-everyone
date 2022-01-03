package web.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import web.common.util.ImageUtil;
import web.item.controller.dto.request.ItemRequest;
import web.item.controller.dto.response.ItemResponse;
import web.item.domain.Item;
import web.item.domain.ItemRepository;
import web.item.domain.category.Category;

import javax.servlet.http.HttpServletRequest;
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

    public Long save(ItemRequest itemInsertRequest, Category category, HttpServletRequest req, HttpServletResponse res, MultipartFile file) throws IOException {
        ImageUtil imageUtil = new ImageUtil();
        String fileName = imageUtil.uploadImage(res, file);
        String path = Paths.get("module-web/src/main/resources/static/images").toAbsolutePath() + File.separator;
        itemInsertRequest.setCategory(category);
        itemInsertRequest.setImageName(fileName);
        itemInsertRequest.setImagePath(path);

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

    public Long update(ItemRequest itemInsertRequest, Category category, HttpServletRequest req, HttpServletResponse res, @RequestParam(required = false) MultipartFile file, Long id) throws IOException {
        Item item = itemRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        ImageUtil imageUtil = new ImageUtil();

        if (file != null) {
            String fileName = imageUtil.uploadImage(res, file);
            String path = Paths.get("module-web/src/main/resources/static/images").toAbsolutePath() + File.separator;

            if (!fileName.equals(item.getImageName())) {
                imageUtil.deleteImage(item.getImagePath() + item.getImageName());
            }

            itemInsertRequest.setImagePath(path);
            itemInsertRequest.setImageName(fileName);
        } else {
            itemInsertRequest.setImageName(item.getImageName());
            itemInsertRequest.setImagePath(item.getImagePath());
        }
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
