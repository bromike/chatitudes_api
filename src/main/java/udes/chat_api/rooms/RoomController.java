package udes.chat_api.rooms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import udes.chat_api.gateway.RoomGateway;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/room")
public class RoomController
{
    @Autowired
    private RoomGateway roomGateway;
    @Autowired
    private RoomAdapter roomAdapter;

    @GetMapping("/")
    public List<RoomDto> getRooms()
    {
        List<Room> rooms = roomGateway.getRooms();

        return rooms.stream()
                .map(room -> roomAdapter.toDto(room))
                .collect(Collectors.toList());          //TODO: needed?
    }

    @PostMapping("/")
    public RoomDto createRoom(@RequestBody RoomDto roomDto)
    {
        Room room = roomAdapter.toEntity(roomDto);

        Room resultRoom = roomGateway.createRoom(room);

        return roomAdapter.toDto(resultRoom);
    }
}
