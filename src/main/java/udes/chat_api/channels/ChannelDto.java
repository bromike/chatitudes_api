package udes.chat_api.channels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelDto
{
    private Integer channelId;
    private int type;
    private int roomId;
    private String name;
    private boolean isPublic;
}
