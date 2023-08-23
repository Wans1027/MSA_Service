package MemberManagement.service;

import MemberManagement.entity.Member;
import MemberManagement.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    public Long registerMember(String name){
        Member member = memberRepository.save(new Member(name));
        return member.getId();
    }

    public List<Member> getMembersInfo(List<Long> memberId){
        return memberRepository.findMembersInfoById(memberId);
    }
}
