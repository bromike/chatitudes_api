package udes.chat_api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.channels.Channel;
import udes.chat_api.channels.ChannelService;

import java.util.List;

@Service
public class ChannelGateway
{
    @Autowired
    private ChannelService channelService;

    public List<Channel> getChannelsByRoomId(int channelId)
    {
        return channelService.getChannelsByRoomId(channelId);
    }

    public Channel createChannel(Channel channel)
    {
        // Check room_privileges
        // Return privilege error if the user does not have the required room_privileges

        return channelService.createChannel(channel);
    }

    public Channel getChannel(int channelId)
    {
        return channelService.getChannel(channelId);
    }

    public List<Channel> searchChannel(String query)
    {
        return channelService.searchChannel(query);
    }

    public Channel updateChannel(Channel channel)
    {
        return channelService.updateChannel(channel);
    }

    public Channel deleteChannel(int channelId)
    {
        return channelService.deleteChannel(channelId);
    }
}
