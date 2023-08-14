package ChattingService.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;


@RequiredArgsConstructor
@Component
@Slf4j
public class StompHandler implements ChannelInterceptor {


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        //log.info("Stomp Handler 실행");
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        // 헤더 토큰 얻기
        //String authorizationHeader = String.valueOf(headerAccessor.getNativeHeader("Authorization"));
        String sessionId = headerAccessor.getSessionId();
        //log.info("sessionId:{}", sessionId);
        return message;
    }

    @EventListener
    public void handleWebSocketConnectionListener(SessionConnectedEvent event){

        Message<byte[]> message = event.getMessage();
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        String sessionId = headerAccessor.getSessionId();
        //log.info("sessionId:{}", sessionId);
        log.info("사용자 입장");
    }

    @EventListener
    public void handleWebSocketDisconnectionListener(SessionDisconnectEvent event){
        log.info("사용자 퇴장");
    }
}
