package udes.chat_api.rooms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity getRooms()
    {
        List<Room> rooms = roomGateway.getRooms();

        return ResponseEntity.status(HttpStatus.OK).body(rooms.stream()
                .map(room -> roomAdapter.toDto(room))
                .collect(Collectors.toList()));
    }

    // TODO: implement user privileges -> Only a certain type of user can create room
    @PostMapping("/room")
    public ResponseEntity createOrUpdateRoom(@RequestBody RoomDto roomDto)
    {
        Room room = roomAdapter.toEntity(roomDto);

        Room roomCreated = roomGateway.createOrUpdateRoom(room);

        if(roomCreated == null || roomCreated.getRoomId() == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Room creation or update failed");
        }

        return ResponseEntity.status(HttpStatus.OK).body(roomAdapter.toDto(roomCreated));
    }

    @PostMapping("/room/search")
    public ResponseEntity searchRoom(@RequestBody String query)
    {
        List<Room> rooms = roomGateway.searchRoom(query);

        return ResponseEntity.status(HttpStatus.OK).body(rooms.stream()
                .map(room -> roomAdapter.toDto(room))
                .collect(Collectors.toList()));
    }

    @GetMapping("/room/{id}")
    public ResponseEntity getRoom(@PathVariable("id") int roomId)
    {
        Room room = roomGateway.getRoom(roomId);

        if(room == null || room.getRoomId() == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Room get failed");
        }

        return ResponseEntity.status(HttpStatus.OK).body(roomAdapter.toDto(room));
    }

    @DeleteMapping("/room/{id}")
    public ResponseEntity deleteRoom(@PathVariable("id") int roomId)
    {
        Room room = roomGateway.deleteRoom(roomId);

        if(room == null || room.getRoomId() == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Room deletion failed");
        }

        return ResponseEntity.status(HttpStatus.OK).body(roomAdapter.toDto(room));
    }
}
