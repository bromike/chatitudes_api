package udes.chat_api.room_privileges;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomPrivilegeDto
{
    private String userCip;
    private int roomId;
    private int type;
}
