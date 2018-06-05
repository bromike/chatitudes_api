package udes.chat_api.rooms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import udes.chat_api.gateway.RoomGateway;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RoomController
{
    @Autowired
    private RoomGateway roomGateway;
    @Autowired
    private RoomAdapter roomAdapter;

    @GetMapping("/room")
    public List<RoomDto> getRooms()
    {
        List<Room> rooms = roomGateway.getRooms();

        return rooms.stream()
                .map(room -> roomAdapter.toDto(room))
                .collect(Collectors.toList());          //TODO: needed?
    }

    @PostMapping("/room")
    public RoomDto createRoom(@RequestBody RoomDto roomDto)
    {
        Room room = roomAdapter.toEntity(roomDto);

        Room resultRoom = roomGateway.createRoom(room);

        return roomAdapter.toDto(resultRoom);
    }
}
