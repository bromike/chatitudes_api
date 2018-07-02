package udes.chat_api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.constants.PrivilegeType;
import udes.chat_api.room_privileges.RoomPrivilege;
import udes.chat_api.room_privileges.RoomPrivilegeRepository;
import udes.chat_api.room_privileges.RoomPrivilegeService;
import udes.chat_api.rooms.Room;
import udes.chat_api.rooms.RoomService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class RoomGateway
{
    @Autowired
    private RoomPrivilegeService roomPrivilegeService;
    @Autowired
    private RoomService roomService;

    public List<Room> getRooms()
    {
        return roomService.getRooms();
    }

    public Room createRoom(Room room)
    {
        // Check room_privileges
        // Return privilege error if the user does not have the required room_privileges

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
        List<Integer> authorizedUser = Arrays.asList(PrivilegeType.admin, PrivilegeType.moderator);

        if(!roomPrivilegeService.userHasRequiredPrivilege("stpe1704", authorizedUser, room.getRoomId()))
        {
            System.out.println("The user does not have the required privileges");
            return null;
        }

        return roomService.updateRoom(room);
    }

    public Room deleteRoom(int roomId)
    {
        List<Integer> authorizedUser = Collections.singletonList(PrivilegeType.admin);

        if(!roomPrivilegeService.userHasRequiredPrivilege("stpe1704", authorizedUser, roomId))
        {
            System.out.println("The user does not have the required privileges");
            return null;
        }

        return roomService.deleteRoom(roomId);
    }
}
