package udes.chat_api.messages;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import udes.chat_api.channels.Channel;
import udes.chat_api.users.User;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "message")
public class Message
{
    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private Integer messageId;

    @Column(name = "message_texte")
    private String content;

    @ManyToOne
    @JoinColumn(name = "channel_id", nullable = false)
    private Channel channel;

    @ManyToOne
    @JoinColumn(name = "message_ownercip", nullable = false)
    private User author;

    @Column(name = "message_timestamp")
    private DateTime time;
}
