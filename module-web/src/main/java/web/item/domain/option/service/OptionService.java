package web.item.domain.option.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.item.domain.Item;
import web.item.domain.option.ItemOption;
import web.item.domain.option.OptionRepository;
import web.item.domain.option.dto.request.ItemOptionListRequest;
import web.item.domain.option.dto.request.ItemOptionRequest;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public void saveAll(ItemOptionListRequest itemOptionList, Item item) {
        List<ItemOption> itemOptions = new ArrayList<>();
        ItemOptionRequest itemOptionRequest;
        for (int i = 0; i < itemOptionList.getItemOptionList().size(); i++) {
            itemOptionList.getItemOptionList().get(i).setItem(item);
            itemOptionRequest = itemOptionList.getItemOptionList().get(i);
            itemOptions.add(itemOptionRequest.toItemOption());
        }

        optionRepository.saveAll(itemOptions);
    }

    public List<ItemOption> findAll() {
        return optionRepository.findAll();
    }

    public List<ItemOption> findAllByItemId(Long itemId) {
        return optionRepository.findAllByItemId(itemId);
    }

    @Transactional
    public void deleteAllByItemId(Long itemId) {
        optionRepository.deleteAllByItemId(itemId);
    }
}
