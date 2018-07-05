package udes.chat_api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.applet.Main;
import udes.chat_api.constants.PrivilegeType;
import udes.chat_api.room_privileges.RoomPrivilege;
import udes.chat_api.room_privileges.RoomPrivilegeRepository;
import udes.chat_api.room_privileges.RoomPrivilegeService;
import udes.chat_api.users.User;

import java.util.Collections;
import java.util.List;

@Service
public class PrivilegeGateway
{
    @Autowired
    private RoomPrivilegeService roomPrivilegeService;
    @Autowired
    private MainGateway mainGateway;

    public RoomPrivilege createOrUpdatePrivilege(RoomPrivilege roomPrivilege)
    {
        User user = mainGateway.getUserFromSecurity();
        int roomId = roomPrivilege.getRoom().getRoomId();
        List<Integer> authorizedUser = Collections.singletonList(PrivilegeType.admin);

        // TODO: removed the hardcoded cip when merging with the CAS authentication
        if(!roomPrivilegeService.userHasRequiredPrivilege(user.getCip(), authorizedUser, roomId))
        {
            System.out.println("The user does not have the required privileges");
            return null;
        }

        return roomPrivilegeService.createOrUpdatePrivilege(roomPrivilege);
    }
}
