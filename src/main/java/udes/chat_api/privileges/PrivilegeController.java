package udes.chat_api.privileges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import udes.chat_api.gateway.PrivilegeGateway;

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

    @GetMapping("/roomprivilege/{roomId}")
    public List<RoomPrivilegeDto> getRoomPrivileges(@PathVariable("roomId") int roomId)
    {
        List<RoomPrivilege> privileges = privilegeGateway.getRoomPrivileges(roomId);

        return privileges.stream()
                .map(privilege -> privilegeAdapter.toDto(privilege))
                .collect(Collectors.toList());
    }

    @PostMapping("/roomprivilege")
    public RoomPrivilegeDto createRoomPrivilege(@RequestBody RoomPrivilegeDto roomPrivilegeDto)
    {
        RoomPrivilege roomPrivilege = privilegeAdapter.toEntity(roomPrivilegeDto);

        RoomPrivilege roomPrivilegeCreated = privilegeGateway.createOrUpdatePrivilege(roomPrivilege);

        return privilegeAdapter.toDto(roomPrivilegeCreated);
    }

    @GetMapping("/channelprivilege/{channelId}")
    public List<ChannelPrivilegeDto> getChannelPrivileges(@PathVariable("channelId") int channelId)
    {
        List<ChannelPrivilege> privileges = privilegeGateway.getChannelPrivileges(channelId);

        return privileges.stream()
                .map(privilege -> privilegeAdapter.toDto(privilege))
                .collect(Collectors.toList());
    }

    @PostMapping("/channelprivilege")
    public ChannelPrivilegeDto createChannelPrivilege(@RequestBody ChannelPrivilegeDto channelPrivilegeDto)
    {
        ChannelPrivilege channelPrivilege = privilegeAdapter.toEntity(channelPrivilegeDto);

        ChannelPrivilege channelPrivilegeCreated = privilegeGateway.createOrUpdatePrivilege(channelPrivilege);

        return privilegeAdapter.toDto(channelPrivilegeCreated);
    }
}
