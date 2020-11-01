package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "member")  //order에 있는 member 필드에 의해 매핑된 거울 이다.
    private List<Order> orders = new ArrayList<>(); //컬렉션 자체를 바꾸지 말고 있는거 그 자체를 사용하기. persiste하면 하이버네이트가 원하는 메커티즘으로 동작 안할 수 있음

}
