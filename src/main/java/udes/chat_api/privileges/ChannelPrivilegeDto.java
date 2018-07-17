package udes.chat_api.privileges;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelPrivilegeDto
{
    private String userCip;
    private int channelId;
    private int type;
}
