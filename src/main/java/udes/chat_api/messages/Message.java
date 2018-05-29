package udes.chat_api.messages;

import org.joda.time.DateTime;

import udes.chat_api.channels.Channel;
import udes.chat_api.users.User;

import javax.persistence.*;

@Entity(name = "message")
public class Message
{
    @Id
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

    public Integer getMessageId()
    {
        return messageId;
    }

    public void setMessageId(Integer messageId)
    {
        this.messageId = messageId;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Channel getChannel()
    {
        return channel;
    }

    public void setChannel(Channel channel)
    {
        this.channel = channel;
    }

    public User getAuthor()
    {
        return author;
    }

    public void setAuthor(User author)
    {
        this.author = author;
    }

    public DateTime getTime()
    {
        return time;
    }

    public void setTime(DateTime time)
    {
        this.time = time;
    }
}
