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
        return channelRepository.findByIsDeletedFalseAndRoomRoomIdOrderByNameAsc(roomId);
    }

    public Channel createChannel(Channel channel)
    {
        return channelRepository.save(channel);
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
            // Error handling, cannot update a channel that does not exist
            return null;
        }

        return channelRepository.save(channel);
    }

    public Channel deleteChannel(int channelId)
    {
        Channel channelToDelete = channelRepository.findByChannelIdAndIsDeletedFalse(channelId);

        if(channelToDelete == null || channelToDelete.isDeleted())
        {
            // Error handling, cannot delete a channel that does not exist or is already deleted
            return null;
        }

        channelToDelete.setDeleted(true);

        return channelRepository.save(channelToDelete);
    }
}
