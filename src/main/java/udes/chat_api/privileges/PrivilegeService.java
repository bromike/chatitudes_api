package udes.chat_api.privileges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.constants.RoomPrivilegeTypes;

import java.util.List;

@Service
public class PrivilegeService
{
    @Autowired
    private RoomPrivilegeRepository roomPrivilegeRepository;
    @Autowired
    private ChannelPrivilegeRepository channelPrivilegeRepository;

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

        // TODO: possible to compare directly User together?
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

    public boolean userHasRequiredPrivilege(String userCip, List<Integer> authorizedPrivilegeLevel, int roomId)
    {
        RoomPrivilege userPrivilege = roomPrivilegeRepository.findByUserCipAndRoomRoomId(userCip, roomId);

        return (userPrivilege != null && authorizedPrivilegeLevel.contains(userPrivilege.getType()));
    }
}
