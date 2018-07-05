package udes.chat_api.room_privileges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import udes.chat_api.gateway.PrivilegeGateway;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class PrivilegeController
{
    @Autowired
    private PrivilegeAdapter privilegeAdapter;
    @Autowired
    private PrivilegeGateway privilegeGateway;

    @PostMapping("/privilege")
    public RoomPrivilegeDto createRoomPrivilege(@RequestBody RoomPrivilegeDto roomPrivilegeDto)
    {
        RoomPrivilege roomPrivilege = privilegeAdapter.toEntity(roomPrivilegeDto);

        RoomPrivilege roomPrivilegeCreated = privilegeGateway.createOrUpdatePrivilege(roomPrivilege);

        return privilegeAdapter.toDto(roomPrivilegeCreated);
    }
}
