package udes.chat_api.rooms;

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
}
