package hello.itemservice.repository.mybatis;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ItemMapper {

    void save(Item item);

    //파라미터가 2개 이상 넘어갈 경우에는 @Param을 붙여준다.
    void update(@Param("id") Long id, @Param("updateParam") ItemUpdateDto updateParam);

    /**
        @Select("select id, item_name, price, quantity from item where id = #{id}")
     * 인터페이스에 위와 같이 직접 애노테이션을 붙여서 쿼리문을 작성하는 것도 가능하다.
     * 하지만, xml와 겹치면 충돌오류가 발생하니 둘 중 하나만 선택하여야 한다.
     */

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond itemSearch);



}
