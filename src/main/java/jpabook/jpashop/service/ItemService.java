package jpabook.jpashop.service;

import jpabook.jpashop.controller.BookForm;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(UpdateItemDto param){
        Book findItem = (Book) itemRepository.findOne(param.getItemId());   //merge를 쓰지 말고 조회해 와서 업데이트할꺼만 세팅을 해준다.

        //setter로 하지말고 사실 entity에 의미있는 메소드를 넣어야함. setter를 쓰지 말자! 그래야 변경 포인트가 1곳이 됨.
        findItem.change(param.getName(), param.getPrice(), param.getStockQuantity(), param.getAuthor(), param.getIsbn());
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

}
