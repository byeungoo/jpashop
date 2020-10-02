package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)  //JPA의 모든 데이터 변경은 트랜잭션 안에서 실행 되야함. 스프링이 제공하는 Transactional 사용하기.
@RequiredArgsConstructor //final있는걸로 생성자 만들어줌
public class MemberService {

    private final MemberRepository memberRepository;  //final로 해두는걸 권장. 컴파일 시점에 체크해줌.

    /*
    // RequiredArgsConstructor 요거 있으니까 얘는 생략
    //@Autowired  //생성자 1개일 경우 스프링이 알아서 주입해줌.
    public MemberService(MemberRepository memberRepository){ //생성자에서 주입. 테스트 케이스 작성하거나 할 때 생성자로 생성해야하기 때문에 놓치지 않음.
        this.memberRepository = memberRepository;
    }
     */

    //회원 가입
    @Transactional //기본이 readOnly지만 여기는 readOnly가 아닌걸로 실행.
    public Long join(Member member){
        //중복회원 검증
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //회원 조회
    //@Transactional(readOnly = true) //읽기 전용이라고 알려주면 조회할 때 최적화 해줌. 더티체킹안함. 데이터베이스에 따라서 읽기 전용이다라고 알려줌.
                                      //읽기에는 가급적 readOnly 써주기
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
