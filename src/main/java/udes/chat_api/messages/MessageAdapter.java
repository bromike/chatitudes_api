package udes.chat_api.messages;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageAdapter
{
    @Autowired
    private MessageRepository messageRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public MessageDto toDto(Message message) {
        MessageDto messageDto = modelMapper.map(message, MessageDto.class);

        return messageDto;
    }

    public Message toEntity(MessageDto messageDto)
    {
        Message message = modelMapper.map(messageDto, Message.class);

        return message;
    }
}
