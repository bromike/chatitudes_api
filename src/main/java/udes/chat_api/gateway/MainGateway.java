package udes.chat_api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import udes.chat_api.channels.Channel;
import udes.chat_api.constants.RoomPrivilegeTypes;
import udes.chat_api.privileges.PrivilegeService;
import udes.chat_api.rooms.Room;
import udes.chat_api.users.User;
import udes.chat_api.users.UserRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class MainGateway
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PrivilegeService privilegeService;

    public User getUserFromSecurity()
    {
        String userCip = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findByCip(userCip);
    }

    public boolean isAdminOrModerator(Room room)
    {
        List<Integer> authorizedUser = Arrays.asList(RoomPrivilegeTypes.admin, RoomPrivilegeTypes.moderator);

        if(!privilegeService.userHasRequiredPrivilege(authorizedUser, room.getRoomId()))
        {
            System.out.println("The user does not have the required privileges");
            return false;
        }

        return true;
    }

}
