package udes.chat_api.channels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService
{
    @Autowired
    private ChannelRepository channelRepository;

    public List<Channel> getChannelsByRoomId(int roomId)
    {
        return channelRepository.findByRoomRoomIdOrderByNameAsc(roomId);
    }

    public Channel createChannel(Channel channel)
    {
        return channelRepository.save(channel);
    }

    public Channel getChannel(int channelId)
    {
        return channelRepository.findByChannelId(channelId);
    }

    public List<Channel> searchChannel(String query)
    {
        return channelRepository.findByNameContaining(query);
    }

    public Channel updateChannel(Channel channel)
    {
        Channel channelToUpdate = channelRepository.findByChannelId(channel.getChannelId());

        if(channelToUpdate == null)
        {
            // Error handling, cannot update a channel that does not exist
            return null;
        }

        return channelRepository.save(channel);
    }

    public void deleteChannel(int channelId)
    {
        channelRepository.deleteChannelByChannelId(channelId);

        // TODO: return delete success/fail
    }
}
