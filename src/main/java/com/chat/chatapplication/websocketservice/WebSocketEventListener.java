package com.chat.chatapplication.websocketservice;

import com.chat.chatapplication.chatmessage.MessageType;
import com.chat.chatapplication.chatmessage.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
@Slf4j
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
     public void handleWebSocketDisconnectListener(SessionDisconnectEvent event)
     {
         StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
         String username =(String) headerAccessor.getSessionAttributes().get("username");
         if (username != null) {
             log.info("User disconnected: " + username);
             LocalTime currentTime = LocalTime.now();
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
             var chatMessage = ChatMessage.builder().type(MessageType.LEAVE).sender(username).messageTime(currentTime.format(formatter)).build();
             messagingTemplate.convertAndSend("/topic/public", chatMessage);
         }
     }
}
