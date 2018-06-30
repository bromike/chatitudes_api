package udes.chat_api.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import udes.chat_api.gateway.MessageGateway;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class MessageController
{
    @Autowired
    private MessageGateway messageGateway;
    @Autowired
    private MessageAdapter messageAdapter;

    @GetMapping("/message")
    public List<MessageDto> getMessagesByChannelId(@RequestParam int channelId)
    {
        List<Message> messages = messageGateway.getMessagesByChannelId(channelId);

        return messages.stream()
                .map(message -> messageAdapter.toDto(message))
                .collect(Collectors.toList());          //TODO: needed?
    }

    @PostMapping("/message")
    public MessageDto createMessage(@RequestBody MessageDto messageDto)
    {
        Message message = messageAdapter.toEntity(messageDto);

        Message messageCreated = messageGateway.createMessage(message);

        return messageAdapter.toDto(messageCreated);
    }

    @PutMapping("/message")
    public MessageDto updateMessage(@RequestBody MessageDto messageDto)
    {
        Message message = messageAdapter.toEntity(messageDto);

        Message updatedMessage = messageGateway.updateMessage(message);

        return messageAdapter.toDto(updatedMessage);
    }

    @GetMapping("/message/{id}")
    public MessageDto getMessage(@PathVariable("id") int messageId)
    {
        Message message = messageGateway.getMessage(messageId);

        return messageAdapter.toDto(message);
    }

    @DeleteMapping("/message/{id}")
    public MessageDto deleteMessage(@PathVariable("id") int messageId)
    {
        Message message = messageGateway.deleteMessage(messageId);

        return messageAdapter.toDto(message);
    }
}
