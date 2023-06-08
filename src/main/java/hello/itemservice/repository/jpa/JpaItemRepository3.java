package hello.itemservice.repository.jpa;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static hello.itemservice.domain.QItem.*;

@Slf4j
@Repository
@Transactional
public class JpaItemRepository3 implements ItemRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public JpaItemRepository3(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }


    @Override
    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        final Item item = em.find(Item.class, itemId);
        item.setItemName(updateParam.getItemName());
        item.setPrice(updateParam.getPrice());
        item.setQuantity(updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        final Item item = em.find(Item.class, id);
        return Optional.ofNullable(item);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {

        final String itemName = cond.getItemName();
        final Integer maxPrice = cond.getMaxPrice();

        final BooleanBuilder builder = new BooleanBuilder();

        /**
         * old version
            if (maxPrice != null) {
                builder.and(item.price.loe(maxPrice));
            }
            if (StringUtils.hasText(itemName)) {
                builder.and(item.itemName.like("%" + itemName + "%"));
            }
        */


        // QItem 을 static import 처리함
        return query.select(item)
                    .from(item)
                    .where(
                            itemNameCheck(itemName, builder),
                            maxPriceCheck(maxPrice, builder)
                    )
                .lef
                    .fetch();
    }

    public BooleanExpression maxPriceCheck(Integer maxPrice, BooleanBuilder builder) {
        if (maxPrice != null) {
            return item.price.loe(maxPrice);
        }
        return null;
    }

    public BooleanExpression itemNameCheck(String itemName, BooleanBuilder builder) {
        if (StringUtils.hasText(itemName)) {
            return item.itemName.like("%" + itemName + "%");
        }
        return null;
    }
}
