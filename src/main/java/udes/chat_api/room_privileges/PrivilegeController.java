package udes.chat_api.room_privileges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import udes.chat_api.gateway.PrivilegeGateway;
import udes.chat_api.rooms.Room;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class PrivilegeController
{
    @Autowired
    private PrivilegeAdapter privilegeAdapter;
    @Autowired
    private PrivilegeGateway privilegeGateway;

    @GetMapping("/privilege/{roomId}")
    public List<RoomPrivilegeDto> getRoomPrivileges(@PathVariable("roomId") int roomId)
    {
        List<RoomPrivilege> privileges = privilegeGateway.getPrivileges(roomId);

        return privileges.stream()
                .map(privilege -> privilegeAdapter.toDto(privilege))
                .collect(Collectors.toList());
    }

    @PostMapping("/privilege")
    public RoomPrivilegeDto createRoomPrivilege(@RequestBody RoomPrivilegeDto roomPrivilegeDto)
    {
        RoomPrivilege roomPrivilege = privilegeAdapter.toEntity(roomPrivilegeDto);

        RoomPrivilege roomPrivilegeCreated = privilegeGateway.createOrUpdatePrivilege(roomPrivilege);

        return privilegeAdapter.toDto(roomPrivilegeCreated);
    }
}
