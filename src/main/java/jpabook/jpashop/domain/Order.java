package jpabook.jpashop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")  //order by 이런거 때문에 관례로 orders로 사용
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)  //order만 저장 해도 같이 저장 됨 (같이 persist됨)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;  //주문시간. 하이버네이트가 알아서 매핑을 해줌

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태

    //==양방향 연관관계 메서드 ==//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);  //양방향 연관관계에 걸리게함.
    }

    //==양방향 연관관계 메서드 ==//
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }



}
