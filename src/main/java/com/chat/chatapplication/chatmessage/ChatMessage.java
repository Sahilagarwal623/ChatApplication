package com.chat.chatapplication.chatmessage;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatMessage {
    private String content;
    private String sender;
    private MessageType type;
    private String messageTime;
    private String roomId;
}
