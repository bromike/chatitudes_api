package udes.chat_api.rooms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import udes.chat_api.gateway.RoomGateway;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8080")
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

        Room roomCreated = roomGateway.createRoom(room);

        return roomAdapter.toDto(roomCreated);
    }

    @PutMapping("/room")
    public RoomDto updateRoom(@RequestBody RoomDto roomDto)
    {
        Room room = roomAdapter.toEntity(roomDto);

        Room updatedRoom = roomGateway.updateRoom(room);

        return roomAdapter.toDto(updatedRoom);
    }

    @PostMapping("/room/search")
    public List<RoomDto> searchRoom(@RequestBody String query)
    {
        List<Room> rooms = roomGateway.searchRoom(query);

        return rooms.stream()
                .map(room -> roomAdapter.toDto(room))
                .collect(Collectors.toList());
    }

    @GetMapping("/room/{id}")
    public RoomDto getRoom(@PathVariable("id") int roomId)
    {
        Room room = roomGateway.getRoom(roomId);

        return roomAdapter.toDto(room);
    }

    @DeleteMapping("/room/{id}")
    public RoomDto deleteRoom(@PathVariable("id") int roomId)
    {
        Room room = roomGateway.deleteRoom(roomId);

        return roomAdapter.toDto(room);
    }
}
