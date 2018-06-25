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
        return messageRepository.findByIsDeletedFalseAndChannelChannelId(channelId);
    }

    public Message createMessage(Message message)
    {
        System.out.print(message.getMessageId());

        return messageRepository.save(message);
    }

    public Message getMessage(int messageId)
    {
        return messageRepository.findByMessageIdAndIsDeletedFalse(messageId);
    }

    public Message updateMessage(Message message)
    {
        Message messageToUpdate = messageRepository.findByMessageIdAndIsDeletedFalse(message.getMessageId());

        if(messageToUpdate == null)
        {
            // Error handling, cannot update a message that does not exist
            return null;
        }

        return messageRepository.save(message);
    }

    public Message deleteMessage(int messageId)
    {
        Message messageToDelete = messageRepository.findByMessageIdAndIsDeletedFalse(messageId);

        if(messageToDelete == null || messageToDelete.isDeleted())
        {
            // Error handling, cannot delete a message that does not exist
            return null;
        }

        messageToDelete.setDeleted(true);

        return messageRepository.save(messageToDelete);
    }
}
