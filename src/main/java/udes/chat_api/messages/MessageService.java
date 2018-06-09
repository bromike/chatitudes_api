package udes.chat_api.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService
{
    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessagesByChannelId(int channelId)
    {
        return messageRepository.findByChannelChannelId(channelId);
    }

    public Message createMessage(Message message)
    {
        System.out.print(message.getMessageId());

        return messageRepository.save(message);
    }

    public Message getMessage(int messageId)
    {
        return messageRepository.findByMessageId(messageId);
    }

    public Message updateMessage(Message message)
    {
        Message messageToUpdate = messageRepository.findByMessageId(message.getMessageId());

        if(messageToUpdate == null)
        {
            // Error handling, cannot update a message that does not exist
            return null;
        }

        return messageRepository.save(message);
    }

    public void deleteMessage(int messageId)
    {
        messageRepository.deleteByMessageId(messageId);

        // TODO: return delete success/fail
    }
}
