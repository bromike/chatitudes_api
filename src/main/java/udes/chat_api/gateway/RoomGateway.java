package udes.chat_api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.rooms.Room;
import udes.chat_api.rooms.RoomService;

import java.util.List;

@Service
public class RoomGateway
{
    @Autowired
    private RoomService roomService;

    public List<Room> getRooms()
    {
        return roomService.getRooms();
    }

    public Room createRoom(Room room)
    {
        // Check privileges
        // Return privilege error if the user does not have the required privileges

        return roomService.createRoom(room);
    }

    public Room getRoom(int roomId)
    {
        return roomService.getRoom(roomId);
    }

    public List<Room> searchRoom(String query)
    {
        return roomService.searchRoom(query);
    }

    public Room updateRoom(Room room)
    {
        return roomService.updateRoom(room);
    }

    public Room deleteRoom(int roomId)
    {
        return roomService.deleteRoom(roomId);
    }
}
