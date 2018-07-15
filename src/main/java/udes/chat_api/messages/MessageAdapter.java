package udes.chat_api.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.channels.ChannelRepository;

@Service
public class MessageAdapter
{
    @Autowired
    private ChannelRepository channelRepository;

    public MessageDto toDto(Message message)
    {
        if(message == null)
        {
            return null;
        }

        MessageDto messageDto = new MessageDto();

        messageDto.setContent(message.getContent());
        messageDto.setMessageId(message.getMessageId());
        messageDto.setTime(message.getTime());
        messageDto.setAuthor(message.getAuthor());

        if(message.getChannel() != null)
        {
            messageDto.setChannelId(message.getChannel().getChannelId());
        }

        return messageDto;
    }

    public Message toEntity(MessageDto messageDto)
    {
        if(messageDto == null)
        {
            return null;
        }

        Message message = new Message();

        message.setMessageId(messageDto.getMessageId());
        message.setAuthor(messageDto.getAuthor());
        message.setChannel(channelRepository.findByChannelIdAndIsDeletedFalse(messageDto.getChannelId()));
        message.setContent(messageDto.getContent());
        message.setTime(messageDto.getTime());

        return message;
    }
}
