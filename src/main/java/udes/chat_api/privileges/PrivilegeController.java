package udes.chat_api.privileges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udes.chat_api.channels.Channel;
import udes.chat_api.constants.RoomPrivilegeTypes;
import udes.chat_api.gateway.PrivilegeGateway;

import java.util.Collections;
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
    @Autowired
    private ChannelPrivilegeRepository channelPrivilegeRepository;
    @Autowired
    private RoomPrivilegeRepository roomPrivilegeRepository;
    @Autowired
    private PrivilegeService privilegeService;

    @GetMapping("/roomprivilege/{roomId}")
    public ResponseEntity getRoomPrivileges(@PathVariable("roomId") int roomId)
    {
        List<RoomPrivilege> privileges = privilegeGateway.getRoomPrivileges(roomId);

        if(privileges == null || privileges.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user is not authorized to acces the room privileges");
        }

        return ResponseEntity.status(HttpStatus.OK).body(privileges.stream()
                .map(privilege -> privilegeAdapter.toDto(privilege))
                .collect(Collectors.toList()));
    }

    @PostMapping("/roomprivilege")
    public ResponseEntity createRoomPrivilege(@RequestBody RoomPrivilegeDto roomPrivilegeDto)
    {
        int roomId = roomPrivilegeDto.getRoomId();
        List<Integer> authorizedUser = Collections.singletonList(RoomPrivilegeTypes.admin);

        if(!privilegeService.userHasRequiredPrivilege(authorizedUser, roomId))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user does not have the required privileges to create a room privilege");
        }

        RoomPrivilege roomPrivilege = privilegeAdapter.toEntity(roomPrivilegeDto);

        RoomPrivilege roomPrivilegeCreated = privilegeGateway.createOrUpdatePrivilege(roomPrivilege);

        if(roomPrivilegeCreated == null || roomPrivilegeCreated.getRoom() == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Room privilege creation failed");
        }

        return ResponseEntity.status(HttpStatus.OK).body(privilegeAdapter.toDto(roomPrivilegeCreated));
    }

    @GetMapping("/channelprivilege/{channelId}")
    public ResponseEntity getChannelPrivileges(@PathVariable("channelId") int channelId)
    {
        List<ChannelPrivilege> privileges = privilegeGateway.getChannelPrivileges(channelId);

        if(privileges == null || privileges.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user is not authorized to acces the channel privileges");
        }

        return ResponseEntity.status(HttpStatus.OK).body(privileges.stream()
                .map(privilege -> privilegeAdapter.toDto(privilege))
                .collect(Collectors.toList()));
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

        return ResponseEntity.status(HttpStatus.OK).body(privilegeAdapter.toDto(channelPrivilegeCreated));
    }

    @DeleteMapping("/roomprivilege/{roomId}/{userCip}")
    public ResponseEntity deleteRoomPrivilege(@PathVariable("roomId") int roomId, @PathVariable("userCip") String userCip)
    {
        privilegeGateway.deleteRoomPrivilege(userCip, roomId);

        RoomPrivilege deletedRoomPrivilege = roomPrivilegeRepository.findByUserCipAndRoomRoomId(userCip, roomId);

        if(deletedRoomPrivilege != null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Room privilege deletion failed");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Room privilege deleted");
    }

    @DeleteMapping("/channelprivilege/{channelId}/{userCip}")
    public ResponseEntity deleteChannelPrivilege(@PathVariable("channelId") int channelId, @PathVariable("userCip") String userCip)
    {
        privilegeGateway.deleteChannelPrivilege(userCip, channelId);

        ChannelPrivilege channelPrivilege = channelPrivilegeRepository.findByUserCipAndChannelChannelId(userCip, channelId);

        if(channelPrivilege != null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Channel privilege deletion failed");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Channel privilege deleted");
    }
}
