package udes.chat_api.channels;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.messages.Message;
import udes.chat_api.messages.MessageDto;
import udes.chat_api.rooms.RoomRepository;

@Service
public class ChannelAdapter
{
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ChannelRepository channelRepository;

    public ChannelDto toDto(Channel channel)
    {
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

        Channel channel = new Channel();

        channel.setChannelId(channelDto.getChannelId());
        channel.setName(channelDto.getName());
        channel.setRoom(roomRepository.findByRoomId(channelDto.getRoomId()));
        channel.setPublic(channelDto.isPublic());
        channel.setType(channelDto.getType());

        return channel;
    }
}
