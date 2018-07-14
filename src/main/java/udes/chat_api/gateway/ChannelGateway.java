package udes.chat_api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.channels.Channel;
import udes.chat_api.channels.ChannelRepository;
import udes.chat_api.channels.ChannelService;

import java.util.List;

@Service
public class ChannelGateway
{
    @Autowired
    private ChannelService channelService;
    @Autowired
    private MainGateway mainGateway;
    @Autowired
    private ChannelRepository channelRepository;

    public List<Channel> getChannelsByRoomId(int channelId)
    {
        return channelService.getChannelsByRoomId(channelId);
    }

    public Channel createChannel(Channel channel)
    {
        if(!mainGateway.isAdminOrModerator(channel.getRoom()))
        {
            return null;
        }

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
        if(!mainGateway.isAdminOrModerator(channel.getRoom()))
        {
            return null;
        }

        return channelService.updateChannel(channel);
    }

    public Channel deleteChannel(int channelId)
    {
        Channel channel = channelRepository.findByChannelIdAndIsDeletedFalse(channelId);

        if(!mainGateway.isAdminOrModerator(channel.getRoom()))
        {
            return null;
        }

        return channelService.deleteChannel(channelId);
    }
}
