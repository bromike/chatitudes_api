package udes.chat_api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.constants.ChannelTypes;
import udes.chat_api.constants.RoomPrivilegeTypes;
import udes.chat_api.messages.Message;
import udes.chat_api.messages.MessageService;
import udes.chat_api.privileges.PrivilegeService;
import udes.chat_api.rooms.Room;
import udes.chat_api.users.User;

import java.util.Arrays;
import java.util.List;

@Service
public class MessageGateway
{
    @Autowired
    private MessageService messageService;
    @Autowired
    private MainGateway mainGateway;
    @Autowired
    private PrivilegeService privilegeService;

    public List<Message> getMessagesByChannelId(int channelId)
    {
        return messageService.getMessagesByChannelId(channelId);
    }

    public Message createMessage(Message message)
    {
        Room room = message.getChannel().getRoom();

        if(!mainGateway.isAdminOrModerator(room))
        {
            return null;
        }

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
