package web.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.item.controller.dto.response.ItemResponse;
import web.item.service.ItemService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/items")
@RestController
public class ItemRestController {

    private final ItemService itemService;

    @GetMapping("")
    public ResponseEntity<List<ItemResponse>> findItemPage(@RequestParam("from") Long from,
                                                           @RequestParam("size") int size) {
        List<ItemResponse> itemResponses = itemService.findPagesBy(from, size);
        return ResponseEntity.ok(itemResponses);
    }

    @GetMapping("/category")
    public ResponseEntity<List<ItemResponse>> findAllItemsByCategory(@RequestParam("category") Long categoryId) {
        List<ItemResponse> itemResponses = itemService.findByCategoryId(categoryId);
        return ResponseEntity.ok(itemResponses);
    }
}
