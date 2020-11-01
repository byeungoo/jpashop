package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore  //양방향의 경우 entity를 한쪽에 JsonIgnore를 해줘야함
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //기본타입이 ORDINAL 인데 중간에 뭐 들어가면 망함. 꼭 String으로 쓰기
    private DeliveryStatus status;  //READY, COMP

}
