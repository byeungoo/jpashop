package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSerarch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/*
 * xToOne(ManyToOne, OneToOne) 성능 최적화
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    private  final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /*
     * 엔티티 직접 노출 방식 (사용X)
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSerarch());

        for(Order order : all){
            order.getMember().getName();  //Lazy 강제 초기화
            order.getDelivery().getAddress();  //Lazy 강제 초기화화
       }

        return all;
    }

    /*
     * 문제점. 3개의 엔티티를 건드림. select 쿼리가 너무 많이 나감.
     */
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2(){
        //ORDER 2개
        //N + 1 -> 1 + 회원 N + 배송 N
        List<Order> orders = orderRepository.findAllByString(new OrderSerarch());

        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();  //fetch join은 100% 이해 해야함.
                                                                           //90%의 성능 문제는 fetch join으로 해결
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    /*
     * 원하는 데이터만 select.
     * v3, v4의 우열을 가리기가 힘듬. v4는 재활용하기 힘듬. 트레이드 오프가 있음.
     * dto로 조회한거는 변경감지 x. v4는 리포지토리 코드가 좀 지저분함.
     * 요새 네트워크 성능이 너무 좋아서 사실 그렇게 차이 안난다.
     * 최적화를 해야한다면 이 v4를 사용할지 고민해야함.
     * repository가 dto를 조회하기 때문에 화면에 의존하는거와 똑같아짐.
     * 그래서 성능 최적화용 repository를 따로 만들어줌.
     */
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4(){
        //return orderRepository.findOrderDtos();
        return orderSimpleQueryRepository.findOrderDtos();  //화면에 dependency한거는 따로 뽑아서 하면 유지보수하기 좋아짐.
    }

    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;  //value object.

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();  //Lazy 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); //Lazy 초기화
        }
    }
}
