package udes.chat_api.messages;

import org.joda.time.DateTime;
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
        return messageRepository.findByIsDeletedFalseAndChannelChannelId(channelId);
    }

    public Message createMessage(Message message)
    {
        message.setTime(DateTime.now());

        return messageRepository.save(message);
    }

    public Message updateMessage(Message message)
    {
        Message messageToUpdate = messageRepository.findByMessageIdAndIsDeletedFalse(message.getMessageId());

        if(messageToUpdate == null)
        {
            System.out.println("The message you are trying to update does not exist");
            return new Message();
        }

        return messageRepository.save(message);
    }

    public Message deleteMessage(Message message)
    {
        if(message == null || message.isDeleted())
        {
            System.out.println("The message you are trying to delete does not exist or is already deleted");
            return new Message();
        }

        message.setDeleted(true);
        return messageRepository.save(message);
    }

    public Message getMessage(int messageId)
    {
        return messageRepository.findByMessageId(messageId);
    }
}
