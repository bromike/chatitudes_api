package udes.chat_api.room_privileges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.constants.PrivilegeType;

import java.util.List;

@Service
public class RoomPrivilegeService
{
    @Autowired
    private RoomPrivilegeRepository roomPrivilegeRepository;

    public List<RoomPrivilege> getPrivileges(int roomId)
    {
        return roomPrivilegeRepository.findByRoomRoomId(roomId);
    }

    public RoomPrivilege createOrUpdatePrivilege(RoomPrivilege roomPrivilege)
    {
        List<RoomPrivilege> admins = roomPrivilegeRepository.findByRoomRoomIdAndType(roomPrivilege.getRoom().getRoomId(), PrivilegeType.admin);

        // TODO: possible to compare directly User together?
        if(admins.size() == 1 && admins.get(0).getUser().getCip().equals(roomPrivilege.getUser().getCip()))
        {
            System.out.println("Cannot change the last admin status");
            return null;
        }

        return roomPrivilegeRepository.save(roomPrivilege);
    }

    public boolean userHasRequiredPrivilege(String userCip, List<Integer> authorizedPrivilegeLevel, int roomId)
    {
        RoomPrivilege userPrivilege = roomPrivilegeRepository.findByUserCipAndRoomRoomId(userCip, roomId);

        return (userPrivilege != null && authorizedPrivilegeLevel.contains(userPrivilege.getType()));
    }
}
