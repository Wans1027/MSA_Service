package MemberManagement.service;

import MemberManagement.entity.Member;
import MemberManagement.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    public Long regMember(String name){
        try{
            Member member = memberRepository.saveAndFlush(new Member(name));
            return member.getId();
        }catch (DataIntegrityViolationException e){
            throw new IllegalArgumentException("이미 존재하는 이름입니다.");
        }
    }

    public List<Member> getMembersInfo(List<Long> memberId){
        return memberRepository.findMembersInfoById(memberId);
    }
}
