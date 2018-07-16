package udes.chat_api.privileges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity createRoomPrivilege(@RequestBody RoomPrivilegeDto roomPrivilegeDto)
    {
        RoomPrivilege roomPrivilege = privilegeAdapter.toEntity(roomPrivilegeDto);

        RoomPrivilege roomPrivilegeCreated = privilegeGateway.createOrUpdatePrivilege(roomPrivilege);

        if(roomPrivilegeCreated == null || roomPrivilegeCreated.getRoom() == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Room privilege creation failed");
        }

        return ResponseEntity.status(HttpStatus.OK).body(roomPrivilegeCreated);
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
    public ResponseEntity createChannelPrivilege(@RequestBody ChannelPrivilegeDto channelPrivilegeDto)
    {
        ChannelPrivilege channelPrivilege = privilegeAdapter.toEntity(channelPrivilegeDto);

        ChannelPrivilege channelPrivilegeCreated = privilegeGateway.createOrUpdatePrivilege(channelPrivilege);

        if(channelPrivilegeCreated == null || channelPrivilegeCreated.getChannel() == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Channel privilege creation failed");
        }

        return ResponseEntity.status(HttpStatus.OK).body(channelPrivilegeCreated);
    }

    @DeleteMapping("/roomprivilege/{roomId}/{userCip}")
    public void deleteRoomPrivilege(@PathVariable("roomId") int roomId, @PathVariable("userCip") String userCip)
    {
        privilegeGateway.deleteRoomPrivilege(userCip, roomId);
    }

    @DeleteMapping("/channelprivilege/{channelId}/{userCip}")
    public void deleteChannelPrivilege(@PathVariable("channelId") int channelId, @PathVariable("userCip") String userCip)
    {
        privilegeGateway.deleteChannelPrivilege(userCip, channelId);
    }
}
