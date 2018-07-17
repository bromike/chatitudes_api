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

    public List<Room> getRooms()
    {
        return roomService.getRooms();
    }

    public Room createOrUpdateRoom(Room room)
    {
        // Check privileges
        // Return privilege error if the user does not have the required privileges

        return roomService.createOrUpdateRoom(room);
    }

    public Room getRoom(int roomId)
    {
        return roomService.getRoom(roomId);
    }

    public List<Room> searchRoom(String query)
    {
        return roomService.searchRoom(query);
    }

    public Room deleteRoom(int roomId)
    {
        List<Integer> authorizedUser = Collections.singletonList(RoomPrivilegeTypes.admin);

        if(!privilegeService.userHasRequiredPrivilege(authorizedUser, roomId))
        {
            System.out.println("The user does not have the required privileges");
            return null;
        }

        return roomService.deleteRoom(roomId);
    }
}
