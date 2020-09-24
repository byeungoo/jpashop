package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository{

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId(); //저장 후 가급적이면 id정도만 조회 하도록 설계
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }

}
