package udes.chat_api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.channels.Channel;
import udes.chat_api.channels.ChannelRepository;
import udes.chat_api.constants.RoomPrivilegeTypes;
import udes.chat_api.privileges.ChannelPrivilege;
import udes.chat_api.privileges.RoomPrivilege;
import udes.chat_api.privileges.PrivilegeService;
import udes.chat_api.users.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class PrivilegeGateway
{
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private MainGateway mainGateway;
    @Autowired
    private ChannelRepository channelRepository;

    public List<RoomPrivilege> getRoomPrivileges(int roomId)
    {
        User user = mainGateway.getUserFromSecurity();
        List<Integer> authorizedUser = Arrays.asList(RoomPrivilegeTypes.admin, RoomPrivilegeTypes.moderator);

        if(!privilegeService.userHasRequiredPrivilege(user.getCip(), authorizedUser, roomId))
        {
            System.out.println("The user does not have the required privileges");
            return null;
        }

        return privilegeService.getRoomPrivileges(roomId);
    }

    public List<ChannelPrivilege> getChannelPrivileges(int channelId)
    {
        User user = mainGateway.getUserFromSecurity();
        int roomId = channelRepository.findByChannelIdAndIsDeletedFalse(channelId).getRoom().getRoomId();
        List<Integer> authorizedUser = Arrays.asList(RoomPrivilegeTypes.admin, RoomPrivilegeTypes.moderator);

        if(!privilegeService.userHasRequiredPrivilege(user.getCip(), authorizedUser, roomId))
        {
            System.out.println("The user does not have the required privileges");
            return null;
        }

        return privilegeService.getChannelPrivileges(channelId);
    }

    public RoomPrivilege createOrUpdatePrivilege(RoomPrivilege roomPrivilege)
    {
        User user = mainGateway.getUserFromSecurity();
        int roomId = roomPrivilege.getRoom().getRoomId();
        List<Integer> authorizedUser = Collections.singletonList(RoomPrivilegeTypes.admin);

        if(!privilegeService.userHasRequiredPrivilege(user.getCip(), authorizedUser, roomId))
        {
            System.out.println("The user does not have the required privileges");
            return null;
        }

        return privilegeService.createOrUpdatePrivilege(roomPrivilege);
    }

    public ChannelPrivilege createOrUpdatePrivilege(ChannelPrivilege channelPrivilege)
    {
        User user = mainGateway.getUserFromSecurity();
        int roomId = channelPrivilege.getChannel().getRoom().getRoomId();
        List<Integer> authorizedUser = Arrays.asList(RoomPrivilegeTypes.admin, RoomPrivilegeTypes.moderator);

        if(!privilegeService.userHasRequiredPrivilege(user.getCip(), authorizedUser, roomId))
        {
            System.out.println("The user does not have the required privileges");
            return null;
        }

        return privilegeService.createOrUpdatePrivilege(channelPrivilege);
    }
}
