package udes.chat_api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.channels.Channel;
import udes.chat_api.channels.ChannelRepository;
import udes.chat_api.constants.RoomPrivilegeTypes;
import udes.chat_api.privileges.ChannelPrivilege;
import udes.chat_api.privileges.RoomPrivilege;
import udes.chat_api.privileges.PrivilegeService;
import udes.chat_api.rooms.Room;
import udes.chat_api.rooms.RoomRepository;

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
    @Autowired
    private RoomRepository roomRepository;

    public List<RoomPrivilege> getRoomPrivileges(int roomId)
    {
        Room room = roomRepository.findByRoomIdAndIsDeletedFalse(roomId);

        if(!mainGateway.isAdminOrModerator(room))
        {
            return null;
        }

        return privilegeService.getRoomPrivileges(roomId);
    }

    public List<ChannelPrivilege> getChannelPrivileges(int channelId)
    {
        Room room = channelRepository.findByChannelIdAndIsDeletedFalse(channelId).getRoom();

        if(!mainGateway.isAdminOrModerator(room))
        {
            return null;
        }

        return privilegeService.getChannelPrivileges(channelId);
    }

    public RoomPrivilege createOrUpdatePrivilege(RoomPrivilege roomPrivilege)
    {
        int roomId = roomPrivilege.getRoom().getRoomId();
        List<Integer> authorizedUser = Collections.singletonList(RoomPrivilegeTypes.admin);

        if(!privilegeService.userHasRequiredPrivilege(authorizedUser, roomId))
        {
            System.out.println("The user does not have the required privileges to create a room privilege");
            return null;
        }

        return privilegeService.createOrUpdatePrivilege(roomPrivilege);
    }

    public ChannelPrivilege createOrUpdatePrivilege(ChannelPrivilege channelPrivilege)
    {
        Room room = channelPrivilege.getChannel().getRoom();

        if(!mainGateway.isAdminOrModerator(room))
        {
            System.out.println("The user does not have the required privileges to create a channel privilege");
            return null;
        }

        return privilegeService.createOrUpdatePrivilege(channelPrivilege);
    }

    public void deleteRoomPrivilege(String userCip, int roomId)
    {
        Room room = roomRepository.findByRoomIdAndIsDeletedFalse(roomId);

        if(!mainGateway.isAdminOrModerator(room))
        {
            return;
        }

        privilegeService.deleteRoomPrivilege(userCip, roomId);
    }

    public void deleteChannelPrivilege(String userCip, int channelId)
    {
        Channel channel = channelRepository.findByChannelIdAndIsDeletedFalse(channelId);

        if(!mainGateway.isAdminOrModerator(channel.getRoom()))
        {
            return;
        }

        privilegeService.deleteChannelPrivilege(userCip, channelId);
    }
}
