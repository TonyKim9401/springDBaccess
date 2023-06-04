package hello.itemservice.repository;

import hello.itemservice.domain.Item;

import java.util.List;
import java.util.Optional;

/**
 * ItemUpdateDto, ItemSearchCond 모두 DTO 지만
 * 마지막에 사용되는 곳이 Repository 이므로 Repository 패키지에 위치시킴
 * 여러군데 에서 사용된다면 따로 패키지를 만들고
 * Service 에서만 사용 된다면 Service 에 위치 시켜도 좋은 방법.
 */
public interface ItemRepository {

    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond cond);

}
