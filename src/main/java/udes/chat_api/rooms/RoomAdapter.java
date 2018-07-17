package udes.chat_api.rooms;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RoomAdapter
{
    private ModelMapper modelMapper = new ModelMapper();

    public RoomDto toDto(Room room)
    {
        if(room == null)
        {
            return null;
        }

        return modelMapper.map(room, RoomDto.class);
    }

    public Room toEntity(RoomDto roomDto)
    {
        if(roomDto == null)
        {
            return null;
        }

        return modelMapper.map(roomDto, Room.class);
    }
}
