package udes.chat_api.channels;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class ChannelAdapter
{
    @Autowired
    private ChannelRepository channelRepository;

    private ModelMapper modelMapper = new ModelMapper();

    private ChannelDto toDto(Channel channel) {
        ChannelDto channelDto = modelMapper.map(channel, ChannelDto.class);

        return channelDto;
    }

    private Channel toEntity(ChannelDto channelDto) throws ParseException {
        Channel channel = modelMapper.map(channelDto, Channel.class);

//        if (channelDto.getChannelId() != null) {
//            Channel oldChannel = channelRepository.findByChannelId(channelDto.getChannelId());
//            channel.setName(oldChannel.getRedditID());
//            channel.setPublic(oldChannel.isSent());
//        }
        return channel;
    }
}
