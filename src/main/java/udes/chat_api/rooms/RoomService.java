package udes.chat_api.rooms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService
{
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getRooms()
    {
        return roomRepository.findByIsDeletedFalse();
    }

    public Room createRoom(Room room)
    {
        return roomRepository.save(room);
    }

    public Room getRoom(int roomId)
    {
        return roomRepository.findByRoomIdAndIsDeletedFalse(roomId);
    }

    public List<Room> searchRoom(String query)
    {
        return roomRepository.findByNameContainingAndIsDeletedFalse(query);
    }

    public Room updateRoom(Room room)
    {
        Room roomToUpdate = roomRepository.findByRoomIdAndIsDeletedFalse(room.getRoomId());

        if(roomToUpdate == null)
        {
            // Error handling, cannot update a room that does not exist
            return null;
        }

        return roomRepository.save(room);
    }

    public Room deleteRoom(int roomId)
    {
        Room roomToDelete = roomRepository.findByRoomIdAndIsDeletedFalse(roomId);

        if(roomToDelete == null || roomToDelete.isDeleted())
        {
            // Error handling, cannot update a room that does not exist
            return null;
        }

        roomToDelete.setDeleted(true);

        return roomRepository.save(roomToDelete);
    }
}
