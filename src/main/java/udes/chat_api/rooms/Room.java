package udes.chat_api.rooms;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
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
}
