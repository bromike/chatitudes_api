package udes.chat_api.channels;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import udes.chat_api.rooms.Room;

@Getter
@Setter
@Entity(name = "channel")
public class Channel
{
    @Id
    @Column(name = "channel_id")
    private Integer channelId;

    @Column(name = "channeltype_id")
    private int type;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "channelName")
    private String name;

    @Column(name = "channel_public")
    private boolean isPublic;
}
