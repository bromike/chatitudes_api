package udes.chat_api.channels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.constants.ChannelPrivilegeTypes;
import udes.chat_api.gateway.MainGateway;
import udes.chat_api.privileges.ChannelPrivilege;
import udes.chat_api.privileges.ChannelPrivilegeRepository;
import udes.chat_api.users.User;

import java.util.List;

@Service
public class ChannelService
{
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ChannelPrivilegeRepository channelPrivilegeRepository;
    @Autowired
    private MainGateway mainGateway;

    public List<Channel> getChannelsByRoomId(int roomId)
    {
        User user = mainGateway.getUserFromSecurity();

        List<Channel> channels = channelRepository.findByIsDeletedFalseAndIsPublicTrueAndRoomRoomId(roomId);
        List<ChannelPrivilege> channelPrivileges = channelPrivilegeRepository.findByUserCip(user.getCip());

        for(ChannelPrivilege channelPrivilege : channelPrivileges)
        {
            int channelRoomId = channelPrivilege.getChannel().getRoom().getRoomId();
            Channel privateChannel = channelRepository.findByIsDeletedFalseAndIsPublicFalseAndChannelId(channelRoomId);

            if(privateChannel != null)
            {
                channels.add(privateChannel);
            }
        }

        return channels;
    }

    public Channel createChannel(Channel channel)
    {
        User user = mainGateway.getUserFromSecurity();

        ChannelPrivilege channelPrivilege = new ChannelPrivilege();
        channelPrivilege.setUser(user);
        channelPrivilege.setChannel(channel);
        channelPrivilege.setType(ChannelPrivilegeTypes.member);

        channelRepository.save(channel);
        channelPrivilegeRepository.save(channelPrivilege);

        return channel;
    }

    public Channel getChannel(int channelId)
    {
        return channelRepository.findByChannelIdAndIsDeletedFalse(channelId);
    }

    public List<Channel> searchChannel(String query)
    {
        return channelRepository.findByNameContainingAndIsDeletedFalse(query);
    }

    public Channel updateChannel(Channel channel)
    {
        Channel channelToUpdate = channelRepository.findByChannelIdAndIsDeletedFalse(channel.getChannelId());

        if(channelToUpdate == null)
        {
            System.out.println("Cannot update a channel that does not exist");
            return null;
        }

        return channelRepository.save(channel);
    }

    public Channel deleteChannel(int channelId)
    {
        Channel channelToDelete = channelRepository.findByChannelIdAndIsDeletedFalse(channelId);

        if(channelToDelete == null || channelToDelete.isDeleted())
        {
            System.out.println("Cannot delete a channel that does not exist");
            return null;
        }

        channelToDelete.setDeleted(true);

        return channelRepository.save(channelToDelete);
    }
}
