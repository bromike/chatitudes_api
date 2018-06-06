package udes.chat_api.channels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import udes.chat_api.gateway.ChannelGateway;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ChannelController
{
    @Autowired
    private ChannelGateway channelGateway;
    @Autowired
    private ChannelAdapter channelAdapter;

    @GetMapping("/channel")
    public List<ChannelDto> getChannelsByRoomId(@RequestParam int channelId)
    {
        List<Channel> channels = channelGateway.getChannelsByRoomId(channelId);

        return channels.stream()
                .map(channel -> channelAdapter.toDto(channel))
                .collect(Collectors.toList());          //TODO: needed?
    }

    @PostMapping("/channel")
    public ChannelDto createChannel(@RequestBody ChannelDto channelDto)
    {
        Channel channel = channelAdapter.toEntity(channelDto);

        Channel channelCreated = channelGateway.createChannel(channel);

        return channelAdapter.toDto(channelCreated);
    }

    @PutMapping("/channel")
    public ChannelDto updateChannel(@RequestBody ChannelDto channelDto)
    {
        Channel channel = channelAdapter.toEntity(channelDto);

        Channel updatedChannel = channelGateway.updateChannel(channel);

        return channelAdapter.toDto(updatedChannel);
    }

    @PostMapping("/channel/search")
    public List<ChannelDto> searchChannel(@RequestBody String query)
    {
        List<Channel> channels = channelGateway.searchChannel(query);

        return channels.stream()
                .map(channel -> channelAdapter.toDto(channel))
                .collect(Collectors.toList());
    }

    @GetMapping("/channel/{id}")
    public ChannelDto getChannel(@PathVariable("id") int channelId)
    {
        Channel channel = channelGateway.getChannel(channelId);

        return channelAdapter.toDto(channel);
    }

    @DeleteMapping("/channel/{id}")
    public void deleteChannel(@PathVariable("id") int channelId)
    {
        channelGateway.deleteChannel(channelId);
    }
}
