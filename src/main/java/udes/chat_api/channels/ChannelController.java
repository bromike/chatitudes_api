package udes.chat_api.channels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udes.chat_api.gateway.ChannelGateway;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class ChannelController
{
    @Autowired
    private ChannelGateway channelGateway;
    @Autowired
    private ChannelAdapter channelAdapter;

    @GetMapping("/channel")
    public ResponseEntity getChannelsByRoomId(@RequestParam int roomId)
    {
        List<Channel> channels = channelGateway.getChannelsByRoomId(roomId);

        return ResponseEntity.status(HttpStatus.OK).body(channels.stream()
                .map(channel -> channelAdapter.toDto(channel))
                .collect(Collectors.toList()));
    }

    @PostMapping("/channel")
    public ResponseEntity createChannel(@RequestBody ChannelDto channelDto)
    {
        Channel channel = channelAdapter.toEntity(channelDto);

        Channel channelCreated = channelGateway.createChannel(channel);

        if(channelCreated == null || channelCreated.getChannelId() == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Channel creation failed");
        }

        return ResponseEntity.status(HttpStatus.OK).body(channelCreated);
    }

    @PutMapping("/channel")
    public ResponseEntity updateChannel(@RequestBody ChannelDto channelDto)
    {
        Channel channel = channelAdapter.toEntity(channelDto);

        Channel updatedChannel = channelGateway.updateChannel(channel);

        if(updatedChannel == null || updatedChannel.getChannelId() == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Channel update failed");
        }

        return ResponseEntity.status(HttpStatus.OK).body(updatedChannel);
    }

    @DeleteMapping("/channel/{id}")
    public ResponseEntity deleteChannel(@PathVariable("id") int channelId)
    {
        Channel channel = channelGateway.deleteChannel(channelId);

        if(channel == null || channel.getChannelId() == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Channel deletion failed");
        }

        return ResponseEntity.status(HttpStatus.OK).body(channel);
    }
}
