package udes.chat_api.messages;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import udes.chat_api.users.User;

@Getter
@Setter
public class MessageDto
{
    private Integer messageId;
    private String content;
    private int channelId;
    private User author;
    private DateTime time;
    private boolean isDeleted;
}
