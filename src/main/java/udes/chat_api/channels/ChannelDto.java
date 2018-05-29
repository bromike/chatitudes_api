package udes.chat_api.channels;

import lombok.Getter;
import lombok.Setter;

import udes.chat_api.rooms.Room;

@Getter
@Setter
public class ChannelDto
{
    private Integer channelId;
    private int type;
    private Room room;
    private String name;
    private boolean isPublic;
}
