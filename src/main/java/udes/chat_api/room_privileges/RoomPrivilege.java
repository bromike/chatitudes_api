package udes.chat_api.room_privileges;

import lombok.Getter;
import lombok.Setter;
import udes.chat_api.rooms.Room;
import udes.chat_api.users.User;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "roomprivilege")
@IdClass(RoomPrivilegeId.class)
public class RoomPrivilege implements Serializable
{
    @Id
    @ManyToOne
    @JoinColumn(name = "user_cip", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "privilegetype_id")
    private int type;
}
