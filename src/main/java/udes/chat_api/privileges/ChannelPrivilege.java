package udes.chat_api.privileges;

import lombok.Getter;
import lombok.Setter;
import udes.chat_api.channels.Channel;
import udes.chat_api.users.User;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "channelprivilege")
@IdClass(ChannelPrivilegeId.class)
public class ChannelPrivilege implements Serializable
{
    @Id
    @ManyToOne
    @JoinColumn(name = "user_cip", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "channel_id", nullable = false)
    private Channel channel;

    @Column(name = "channelprivilegetype_id", nullable = false)
    private int type;
}