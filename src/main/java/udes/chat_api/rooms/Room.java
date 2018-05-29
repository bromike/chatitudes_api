package udes.chat_api.rooms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "room")
public class Room
{
    @Id
    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "room_name")
    private String name;

    @Column(name = "room_public")
    private boolean isPublic;

    public Integer getRoomId()
    {
        return roomId;
    }

    public void setRoomId(Integer roomId)
    {
        this.roomId = roomId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isPublic()
    {
        return isPublic;
    }

    public void setPublic(boolean aPublic)
    {
        isPublic = aPublic;
    }
}
