package udes.chat_api.privileges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.constants.RoomPrivilegeTypes;
import udes.chat_api.gateway.MainGateway;
import udes.chat_api.users.User;

import java.util.List;

@Service
public class PrivilegeService
{
    @Autowired
    private RoomPrivilegeRepository roomPrivilegeRepository;
    @Autowired
    private ChannelPrivilegeRepository channelPrivilegeRepository;
    @Autowired
    private MainGateway mainGateway;

    public List<RoomPrivilege> getRoomPrivileges(int roomId)
    {
        return roomPrivilegeRepository.findByRoomRoomId(roomId);
    }

    public List<ChannelPrivilege> getChannelPrivileges(int channelId)
    {
        return channelPrivilegeRepository.findByChannelChannelId(channelId);
    }

    public RoomPrivilege createOrUpdatePrivilege(RoomPrivilege roomPrivilege)
    {
        List<RoomPrivilege> admins = roomPrivilegeRepository.findByRoomRoomIdAndType(roomPrivilege.getRoom().getRoomId(), RoomPrivilegeTypes.admin);

        if(roomPrivilege.getUser() == null)
        {
            System.out.println("No user associated to the room privilege");
            return null;
        }

        if(admins.size() == 1 && admins.get(0).getUser().getCip().equals(roomPrivilege.getUser().getCip()))
        {
            System.out.println("Cannot change the last admin status");
            return null;
        }

        return roomPrivilegeRepository.save(roomPrivilege);
    }

    public ChannelPrivilege createOrUpdatePrivilege(ChannelPrivilege channelPrivilege)
    {
        return channelPrivilegeRepository.save(channelPrivilege);
    }

    public boolean userHasRequiredPrivilege(List<Integer> authorizedPrivilegeLevel, int roomId)
    {
        User user = mainGateway.getUserFromSecurity();
        RoomPrivilege userPrivilege = roomPrivilegeRepository.findByUserCipAndRoomRoomId(user.getCip(), roomId);

        return (userPrivilege != null && authorizedPrivilegeLevel.contains(userPrivilege.getType()));
    }

    public boolean userIsMemberOfRoom(int roomId)
    {
        User user = mainGateway.getUserFromSecurity();
        RoomPrivilege roomPrivilege = roomPrivilegeRepository.findByUserCipAndRoomRoomId(user.getCip(), roomId);

        return roomPrivilege != null;
    }

    public ChannelPrivilege getUserChannelPrivilege(int channelId)
    {
        User user = mainGateway.getUserFromSecurity();

        return channelPrivilegeRepository.findByUserCipAndChannelChannelId(user.getCip(), channelId);
    }

    public void deleteRoomPrivilege(String userCip, int roomId)
    {
        RoomPrivilege roomPrivilege = roomPrivilegeRepository.findByUserCipAndRoomRoomId(userCip, roomId);

        List<RoomPrivilege> admins = roomPrivilegeRepository.findByRoomRoomIdAndType(roomPrivilege.getRoom().getRoomId(), RoomPrivilegeTypes.admin);

        if(admins.size() == 1 && admins.get(0).getUser().getCip().equals(roomPrivilege.getUser().getCip()))
        {
            System.out.println("Cannot delete the last admin of a room");
            return;
        }

        roomPrivilegeRepository.delete(roomPrivilege);
    }

    public void deleteChannelPrivilege(String userCip, int channelId)
    {
        ChannelPrivilege channelPrivilege = channelPrivilegeRepository.findByUserCipAndChannelChannelId(userCip, channelId);

        if(channelPrivilege == null)
        {
            System.out.println("Cannot delete a channel privilege that does not exist");
            return;
        }

        channelPrivilegeRepository.delete(channelPrivilege);
    }
}
