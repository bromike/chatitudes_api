package udes.chat_api.rooms;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.utils.Converter;

import java.util.List;

@Service
public class RoomService
{
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getRooms()
    {
        return roomRepository.findAll();
    }

    public Room createRoom(Room room)
    {
        return roomRepository.save(room);
    }

    public Room getRoom(int roomId)
    {
        return roomRepository.findByRoomId(roomId);
    }

    public List<Room> searchRoom(String query)
    {
        return roomRepository.findByNameContaining(query);
    }

    public Room updateRoom(Room room)
    {
        Room roomToUpdate = roomRepository.findByRoomId(room.getRoomId());

        if(roomToUpdate == null)
        {
            // Error handling, cannot update a room that does not exist
            return null;
        }

        return roomRepository.save(room);
    }

    public void deleteRoom(int roomId)
    {
        roomRepository.deleteByRoomId(roomId);

        // TODO: return delete success/fail
    }
}
