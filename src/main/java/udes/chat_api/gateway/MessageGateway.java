package udes.chat_api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.channels.Channel;
import udes.chat_api.constants.ChannelPrivilegeTypes;
import udes.chat_api.constants.ChannelTypes;
import udes.chat_api.messages.Message;
import udes.chat_api.messages.MessageService;
import udes.chat_api.privileges.ChannelPrivilege;
import udes.chat_api.privileges.PrivilegeService;
import udes.chat_api.rooms.Room;
import udes.chat_api.users.User;

import java.util.List;

@Service
public class MessageGateway
{
    @Autowired
    private MessageService messageService;
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private MainGateway mainGateway;

    public List<Message> getMessagesByChannelId(int channelId)
    {
        return messageService.getMessagesByChannelId(channelId);
    }

    public Message createMessage(Message message)
    {
        User user = mainGateway.getUserFromSecurity();
        User messageAuthor = message.getAuthor();
        Channel channel = message.getChannel();
        ChannelPrivilege channelPrivilege = privilegeService.getUserChannelPrivilege(channel.getChannelId());
        Room room = channel.getRoom();

        if(channel.getType() == ChannelTypes.restricted && !mainGateway.isAdminOrModerator(message.getChannel().getRoom()))
        {
            System.out.println("The user is trying to create a message in a restricted channel where he is not a moderator");
            return null;
        }
        else if(channelPrivilege.getType() == ChannelPrivilegeTypes.muted || channelPrivilege.getType() == ChannelPrivilegeTypes.banned)
        {
            System.out.println("The user is trying to create a message in a channel where he is muted or banned");
            return null;
        }
        else if(!channel.isPublic() && channelPrivilege.getType() != ChannelPrivilegeTypes.member)
        {
            System.out.println("The user is trying to create a message in a private channel that he is not member of");
            return null;
        }
        else if(!room.isPublic() && !privilegeService.userIsMemberOfRoom(room.getRoomId()))
        {
            System.out.println("The user is trying to create a message in a private room that he is not member of");
            return null;
        }
        else if(!user.getCip().equals(messageAuthor.getCip()))
        {
            System.out.println("Security context user and message author are not the same");
            return null;
        }

        return messageService.createMessage(message);
    }

    public Message getMessage(int messageId)
    {
        Message message = messageService.getMessage(messageId);

        if(!isOwnerOrModerator(message))
        {
            System.out.println("The user is trying to access someone else's message and is not a moderator");
            return null;
        }

        return messageService.getMessage(messageId);
    }

    public Message updateMessage(Message message)
    {
        if(!isOwnerOrModerator(message))
        {
            System.out.println("The user is trying to update someone else's message and is not a moderator");
            return null;
        }

        return messageService.updateMessage(message);
    }

    public Message deleteMessage(int messageId)
    {
        Message message = messageService.getMessage(messageId);

        if(!isOwnerOrModerator(message))
        {
            System.out.println("The user is trying to delete someone else's message and is not a moderator");
            return null;
        }

        return messageService.deleteMessage(message);
    }

    private boolean isOwnerOrModerator(Message message)
    {
        User user = mainGateway.getUserFromSecurity();

        return message != null && (message.getAuthor().getCip().equals(user.getCip()) || mainGateway.isAdminOrModerator(message.getChannel().getRoom()));
    }
}
