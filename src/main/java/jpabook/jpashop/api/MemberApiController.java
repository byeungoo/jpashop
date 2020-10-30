package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){  //Entity가 변하면 API 스펙이 바뀜. 이렇게 쓰면 안됨.
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){  //Entity가 변하면 API 스펙이 바뀜. 이렇게 쓰면 안됨.
        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest request) {  //Entity가 변하면 API 스펙이 바뀜. 이렇게 쓰면 안됨.
        memberService.update(id, request.getName());
        Member member = memberService.findOne(id);  //트래픽이 그렇게 크지 않다면 pk로 한번 더 조회하는거 정도는 크게 문제 안됨.
        return new UpdateMemberResponse(member.getName(), member.getId());
    }

    //이 안에서만 쓸꺼니까 내부 클래스로 만들어도 됨
    @Data
    static class CreateMemberRequest{
        private String name;
    }

    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class UpdateMemberRequest{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private String name;
        private Long id;
    }

}
