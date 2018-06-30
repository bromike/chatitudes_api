package udes.chat_api.rooms;

import lombok.Getter;
import lombok.Setter;
import udes.chat_api.channels.Channel;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "room")
public class Room
{
    @Id
    @GeneratedValue
    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "room_name")
    private String name;

    @Column(name = "room_public")
    private boolean isPublic;

    @Column(name = "room_deleted")
    private boolean isDeleted;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "room")
//    private List<Channel> channels;
}
