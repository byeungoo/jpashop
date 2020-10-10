package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("B")
public class Book extends Item{

    private String author;
    private String isbn;

    public void change(String name, int price, int stockQuantity, String author, String isbn){
        this.setName(name);
        this.setPrice(price);
        this.setStockQuantity(stockQuantity);
        this.author = author;
        this. isbn = isbn;
    }

}
