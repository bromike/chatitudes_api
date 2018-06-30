package udes.chat_api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.messages.Message;
import udes.chat_api.messages.MessageService;

import java.util.List;

@Service
public class MessageGateway
{
    @Autowired
    private MessageService messageService;

    public List<Message> getMessagesByChannelId(int channelId)
    {
        return messageService.getMessagesByChannelId(channelId);
    }

    public Message createMessage(Message message)
    {
        return messageService.createMessage(message);
    }

    public Message getMessage(int messageId)
    {
        return messageService.getMessage(messageId);
    }

    public Message updateMessage(Message message)
    {
        return messageService.updateMessage(message);
    }

    public Message deleteMessage(int messageId)
    {
        return messageService.deleteMessage(messageId);
    }
}
