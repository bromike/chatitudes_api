package udes.chat_api.channels;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ChannelAdapter
{
    private ModelMapper modelMapper = new ModelMapper();

    public ChannelDto toDto(Channel channel)
    {
        return modelMapper.map(channel, ChannelDto.class);
    }

    public Channel toEntity(ChannelDto channelDto)
    {
        return modelMapper.map(channelDto, Channel.class);
    }
}
