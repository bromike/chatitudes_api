package udes.chat_api.rooms;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomAdapter
{
    @Autowired
    private RoomRepository roomRepository;

    private ModelMapper modelMapper = new ModelMapper();

    private RoomDto toDto(Room room) {
        RoomDto roomDto = modelMapper.map(room, RoomDto.class);

        return roomDto;
    }

    private Room toEntity(RoomDto roomDto)
    {
        Room room = modelMapper.map(roomDto, Room.class);

        return room;
    }
}
