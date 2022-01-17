package web.order.domain.item.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.order.domain.item.OrderItemRepository;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public int findSellCount(Long id) {

        Long sellCount = orderItemRepository.countByItemId(id);
        System.out.println("sellCount = " + sellCount);
        return Integer.parseInt(String.valueOf(sellCount));
    }

    public int findSellPrice(Long id) {

        Long sellPrice = orderItemRepository.findByItemId(id);
        if (sellPrice == null) {
            System.out.println("null" + sellPrice);
        } else {
            System.out.println("sellPrice = " + sellPrice);
        }
        return Integer.parseInt(String.valueOf(sellPrice));
    }
}