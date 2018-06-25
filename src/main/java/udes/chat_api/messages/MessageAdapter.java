package udes.chat_api.messages;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.channels.ChannelRepository;
import udes.chat_api.users.UserRepository;

@Service
public class MessageAdapter
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChannelRepository channelRepository;


    private ModelMapper modelMapper = new ModelMapper();

    public MessageDto toDto(Message message)
    {
        //return modelMapper.map(message, MessageDto.class);

        MessageDto messageDto = new MessageDto();

        messageDto.setContent(message.getContent());
        messageDto.setMessageId(message.getMessageId());
        messageDto.setTime(message.getTime());
        messageDto.setChannelId(message.getChannel().getChannelId());
        messageDto.setUserCip(message.getAuthor().getCip());

        return messageDto;
    }

    public Message toEntity(MessageDto messageDto)
    {
        //return modelMapper.map(messageDto, Message.class);

        Message message = new Message();

        message.setMessageId(messageDto.getMessageId());
        message.setAuthor(userRepository.findByCip(messageDto.getUserCip()));
        message.setChannel(channelRepository.findByChannelId(messageDto.getChannelId()));
        message.setContent(messageDto.getContent());
        message.setTime(messageDto.getTime());

        return message;
    }
}
