package udes.chat_api.rooms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDto
{
    private Integer roomId;
    private String name;
    private boolean isPublic;
}
