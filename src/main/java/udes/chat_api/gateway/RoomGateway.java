package udes.chat_api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.constants.RoomPrivilegeTypes;
import udes.chat_api.privileges.PrivilegeService;
import udes.chat_api.rooms.Room;
import udes.chat_api.rooms.RoomService;
import udes.chat_api.users.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class RoomGateway
{
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private MainGateway mainGateway;

    public List<Room> getRooms()
    {
        return roomService.getRooms();
    }

    public Room createRoom(Room room)
    {
        // Check privileges
        // Return privilege error if the user does not have the required privileges

        return roomService.createRoom(room);
    }

    public Room getRoom(int roomId)
    {
        return roomService.getRoom(roomId);
    }

    public List<Room> searchRoom(String query)
    {
        return roomService.searchRoom(query);
    }

    public Room updateRoom(Room room)
    {
        User user = mainGateway.getUserFromSecurity();
        List<Integer> authorizedUser = Arrays.asList(RoomPrivilegeTypes.admin, RoomPrivilegeTypes.moderator);

        if(!privilegeService.userHasRequiredPrivilege(user.getCip(), authorizedUser, room.getRoomId()))
        {
            System.out.println("The user does not have the required privileges");
            return null;
        }

        return roomService.updateRoom(room);
    }

    public Room deleteRoom(int roomId)
    {
        User user = mainGateway.getUserFromSecurity();
        List<Integer> authorizedUser = Collections.singletonList(RoomPrivilegeTypes.admin);

        if(!privilegeService.userHasRequiredPrivilege(user.getCip(), authorizedUser, roomId))
        {
            System.out.println("The user does not have the required privileges");
            return null;
        }

        return roomService.deleteRoom(roomId);
    }
}
