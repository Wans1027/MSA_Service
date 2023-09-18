package MemberManagement.service;

import MemberManagement.entity.Member;
import MemberManagement.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;



    @Test
    @Transactional
    void 회원가입중복검사() {
        Long memberId = memberService.regMember("kim");
        //중복검사
        Assertions.assertThatThrownBy(() -> memberService.regMember("kim"))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    @Transactional
    void getMembersInfo() {
        Long a = memberService.regMember("a");
        Long b = memberService.regMember("b");
        Long c = memberService.regMember("c");
        List<Long> mIds = new ArrayList<>();
        mIds.add(a);
        mIds.add(b);
        mIds.add(c);
        List<Member> membersInfo = memberService.getMembersInfo(mIds);
        Assertions.assertThat(membersInfo.size()).isEqualTo(mIds.size());
        for (int i = 0; i < membersInfo.size(); i++) {
            Assertions.assertThat(membersInfo.get(i).getId()).isEqualTo(mIds.get(i));
        }
    }

}