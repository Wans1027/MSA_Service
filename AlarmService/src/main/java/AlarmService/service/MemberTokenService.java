package AlarmService.service;

import AlarmService.entity.MemberToken;
import AlarmService.repository.MemberTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberTokenService {
    private final MemberTokenRepository memberTokenRepository;
    private final NotificationServiceImpl notificationService;
    @Transactional
    @CachePut(value = "Token", key = "#memberId", cacheManager = "contentCacheManager")
    public MemberToken saveMemberDeviceToken(Long memberId, String token){
        try{
            MemberToken memberToken = memberTokenRepository.findByMemberId(memberId).orElseThrow();
            memberToken.setToken(token);
            return memberToken;
        }catch (NoSuchElementException e){
            return memberTokenRepository.save(new MemberToken(memberId, token));
        }
    }

    @Transactional
    @Cacheable(value = "Token", key = "#memberId", cacheManager = "contentCacheManager")
    public MemberToken getToken(Long memberId){
        return memberTokenRepository.findByMemberId(memberId).orElseThrow();
    }

    @Transactional
    public List<String> getTokenList(List<Long> memberIdList){
        List<String> tokenList = new ArrayList<>();
        memberIdList.forEach(id -> tokenList.add(getToken(id).getToken()));
        //return memberTokenRepository.findTokensByMemberIdList(memberIdList);
        return tokenList;
    }

    public void sendPushAlarm(List<String> tokenList, String title, String body, String type, Long detailId){
        notificationService.sendByTokenList(tokenList, title, body, type, detailId);
    }

}
