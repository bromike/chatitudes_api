package udes.chat_api.messages;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

@Getter
@Setter
public class MessageDto
{
    private Integer messageId;
    private String content;
    private int channelId;
    private String userCip;
    private DateTime time;
    private boolean isDeleted;
}
