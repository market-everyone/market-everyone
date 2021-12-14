package web.item.domain.option.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ItemOptionListRequest {

    private List<ItemOptionRequest> itemOptionList;
}
