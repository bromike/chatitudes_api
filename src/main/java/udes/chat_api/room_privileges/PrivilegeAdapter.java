package udes.chat_api.room_privileges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.rooms.RoomRepository;
import udes.chat_api.users.UserRepository;

@Service
public class PrivilegeAdapter
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomPrivilegeRepository roomPrivilegeRepository;

    public RoomPrivilegeDto toDto(RoomPrivilege roomPrivilege)
    {
        RoomPrivilegeDto roomPrivilegeDto = new RoomPrivilegeDto();

        roomPrivilegeDto.setUserCip(roomPrivilege.getUser().getCip());

        roomPrivilegeDto.setRoomId(roomPrivilege.getRoom().getRoomId());

        roomPrivilegeDto.setType(roomPrivilege.getType());

        return roomPrivilegeDto;
    }

    public RoomPrivilege toEntity(RoomPrivilegeDto roomPrivilegeDto)
    {
        RoomPrivilege existingRoomPrivilege = roomPrivilegeRepository.findByUserCipAndRoomRoomId(roomPrivilegeDto.getUserCip(), roomPrivilegeDto.getRoomId());

        if(existingRoomPrivilege != null)
        {
            existingRoomPrivilege.setType(roomPrivilegeDto.getType());
            return existingRoomPrivilege;
        }

        RoomPrivilege roomPrivilege = new RoomPrivilege();

        roomPrivilege.setUser(userRepository.findByCip(roomPrivilegeDto.getUserCip()));
        roomPrivilege.setRoom(roomRepository.findByRoomIdAndIsDeletedFalse(roomPrivilegeDto.getRoomId()));
        roomPrivilege.setType(roomPrivilegeDto.getType());

        return roomPrivilege;
    }
}
