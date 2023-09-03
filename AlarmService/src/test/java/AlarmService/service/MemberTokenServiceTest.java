package AlarmService.service;

import AlarmService.entity.MemberToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class MemberTokenServiceTest {
    @Autowired
    MemberTokenService memberTokenService;

    @BeforeEach
    void setUp() {
        memberTokenService.saveMemberDeviceToken(1L, "FCMToken");
    }

    @Test
    void saveMemberDeviceToken() {
        System.out.println("시작");
        MemberToken fcmToken2 = memberTokenService.saveMemberDeviceToken(1L, "FCMToken2");
        System.out.println(fcmToken2.getToken());

    }

    @Test
    void getToken() {
        System.out.println("시작");
        MemberToken token = memberTokenService.getToken(1L);
        System.out.println(token.getToken());
    }

    @Test
    void getTokenList() {
    }

    @Test
    void sendPushAlarm() {
    }
}