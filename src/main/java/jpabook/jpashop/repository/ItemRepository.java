package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){
            em.persist(item);
        } else{
            em.merge(item);  // merge란 itemId로 아이템을 찾고, 파라미터로 넘어온 값으로 필드값을 다 바꿔치기함.
                             // 파라미터로 넘어온 값은 영속성 컨텍스트에서 관리안함
                             // 또한 null로 온 값은 다 null로 업데이트함.. 그래서 위험함.
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
