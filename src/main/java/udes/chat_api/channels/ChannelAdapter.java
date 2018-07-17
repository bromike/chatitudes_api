package udes.chat_api.channels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.rooms.RoomRepository;

@Service
public class ChannelAdapter
{
    @Autowired
    private RoomRepository roomRepository;

    public ChannelDto toDto(Channel channel)
    {
        if(channel == null)
        {
            return null;
        }

        ChannelDto channelDto = new ChannelDto();

        channelDto.setChannelId(channel.getChannelId());
        channelDto.setName(channel.getName());
        channelDto.setPublic(channel.isPublic());
        channelDto.setType(channel.getType());
        channelDto.setRoomId(channel.getRoom().getRoomId());

        return channelDto;
    }

    public Channel toEntity(ChannelDto channelDto)
    {
        if(channelDto == null)
        {
            return null;
        }

        Channel channel = new Channel();

        channel.setChannelId(channelDto.getChannelId());
        channel.setName(channelDto.getName());
        channel.setRoom(roomRepository.findByRoomIdAndIsDeletedFalse(channelDto.getRoomId()));
        channel.setPublic(channelDto.isPublic());
        channel.setType(channelDto.getType());

        return channel;
    }
}
