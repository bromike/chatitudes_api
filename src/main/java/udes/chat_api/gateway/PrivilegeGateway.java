package udes.chat_api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.constants.PrivilegeType;
import udes.chat_api.room_privileges.RoomPrivilege;
import udes.chat_api.room_privileges.RoomPrivilegeRepository;
import udes.chat_api.room_privileges.PrivilegeService;

@Service
public class PrivilegeGateway
{
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private RoomPrivilegeRepository roomPrivilegeRepository;

    public RoomPrivilege createOrUpdatePrivilege(RoomPrivilege roomPrivilege)
    {
        int roomId = roomPrivilege.getRoom().getRoomId();
        // TODO: removed the hardcoded cip when merging with the CAS authentication
        RoomPrivilege userPrivilegeLevel = roomPrivilegeRepository.findByUserCipAndRoomRoomId("stpe1704", roomId);

        if(userPrivilegeLevel == null || userPrivilegeLevel.getType() < PrivilegeType.admin)
        {
            // The user does not have the required level of roomPrivilege
            return null;
        }

        return privilegeService.createOrUpdatePrivilege(roomPrivilege);
    }
}
