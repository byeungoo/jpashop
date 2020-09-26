package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable //jpa 내장 타입
@Getter
public class Address {  //변경되지 않게 설계. Setter 제공 x

    private String city;
    private String street;
    private String zipcode;

    protected Address() {  //JPA가 프록시 리플랙션을 써야하는 경우가 많은데 기본생성자가 없으면 못쓸 수 있음.
                           //public으로 만들면 사람들이 쓸 수 있으므로 protected로 해둠. JPA 스펙상 만든거다!
    }

    public Address(String city, String street, String zipcode) {  //생성될 때만 값 세팅
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
