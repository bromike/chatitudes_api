package udes.chat_api.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity getMessagesByChannelId(@RequestParam int channelId)
    {
        List<Message> messages = messageGateway.getMessagesByChannelId(channelId);

        return ResponseEntity.status(HttpStatus.OK).body(messages.stream()
                .map(message -> messageAdapter.toDto(message))
                .collect(Collectors.toList()));
    }

    @PostMapping("/message")
    public ResponseEntity createMessage(@RequestBody MessageDto messageDto)
    {
        Message message = messageAdapter.toEntity(messageDto);

        Message messageCreated = messageGateway.createMessage(message);

        if(messageCreated == null || messageCreated.getMessageId() == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message creation failed");
        }

        return ResponseEntity.status(HttpStatus.OK).body(messageAdapter.toDto(messageCreated));
    }

    @PutMapping("/message")
    public ResponseEntity updateMessage(@RequestBody MessageDto messageDto)
    {
        Message message = messageAdapter.toEntity(messageDto);

        Message updatedMessage = messageGateway.updateMessage(message);

        if(updatedMessage == null || updatedMessage.getMessageId() == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message update failed");
        }

        return ResponseEntity.status(HttpStatus.OK).body(messageAdapter.toDto(updatedMessage));
    }

    @GetMapping("/message/{id}")
    public ResponseEntity getMessage(@PathVariable("id") int messageId)
    {
        Message message = messageGateway.getMessage(messageId);

        if(message == null || message.getMessageId() == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message get failed");
        }

        return ResponseEntity.status(HttpStatus.OK).body(messageAdapter.toDto(message));
    }

    @DeleteMapping("/message/{id}")
    public ResponseEntity deleteMessage(@PathVariable("id") int messageId)
    {
        Message message = messageGateway.deleteMessage(messageId);

        if(message == null || message.getMessageId() == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message deletion failed");
        }

        return ResponseEntity.status(HttpStatus.OK).body(messageAdapter.toDto(message));
    }
}
