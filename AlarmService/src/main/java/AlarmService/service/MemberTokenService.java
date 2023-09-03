package AlarmService.service;

import AlarmService.entity.MemberToken;
import AlarmService.repository.MemberTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 만약 채팅기능에서 채팅을 보내면 매번의 채팅마다 토큰이 조회되어야 하는데 이 문제의 해결법은?
     * 레디스를 활용!
     * 그럼 레디스는 채팅서버에 둘까 알람서버에 둘까?
     *
     */
    @Transactional
    @Cacheable(value = "Token", key = "#memberId", cacheManager = "contentCacheManager")
    public MemberToken getToken(Long memberId){
        return memberTokenRepository.findByMemberId(memberId).orElseThrow();
    }

    @Transactional
    public List<String> getTokenList(List<Long> memberIdList){
        return memberTokenRepository.findTokensByMemberIdList(memberIdList);
    }

    public void sendPushAlarm(List<String> tokenList, String title, String body, String type, Long detailId){
        notificationService.sendByTokenList(tokenList, title, body, type, detailId);
    }

}
