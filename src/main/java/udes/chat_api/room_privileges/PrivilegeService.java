package udes.chat_api.room_privileges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.constants.PrivilegeType;

import java.util.List;

@Service
public class PrivilegeService
{
    @Autowired
    private RoomPrivilegeRepository roomPrivilegeRepository;

    public RoomPrivilege createOrUpdatePrivilege(RoomPrivilege roomPrivilege)
    {
        List<RoomPrivilege> admins = roomPrivilegeRepository.findByRoomRoomIdAndType(roomPrivilege.getRoom().getRoomId(), PrivilegeType.admin);

        // TODO: possible to compare directly User together?
        if(admins.size() == 1 && admins.get(0).getUser().getCip().equals(roomPrivilege.getUser().getCip()))
        {
            // Cannot change the last admin status
            return null;
        }

        return roomPrivilegeRepository.save(roomPrivilege);
    }
}
