package udes.chat_api.rooms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.constants.RoomPrivilegeTypes;
import udes.chat_api.gateway.MainGateway;
import udes.chat_api.privileges.RoomPrivilege;
import udes.chat_api.privileges.RoomPrivilegeRepository;
import udes.chat_api.privileges.PrivilegeService;
import udes.chat_api.users.User;

import java.util.Arrays;
import java.util.List;

@Service
public class RoomService
{
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomPrivilegeRepository roomPrivilegeRepository;
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private MainGateway mainGateway;

    public List<Room> getRooms()
    {
        User user = mainGateway.getUserFromSecurity();

        List<Room> rooms = roomRepository.findByIsDeletedFalseAndIsPublicTrue();
        List<RoomPrivilege> roomPrivileges = roomPrivilegeRepository.findByUserCip(user.getCip());

        for(RoomPrivilege roomPrivilege : roomPrivileges)
        {
            Room privateRoom = roomRepository.findByRoomIdAndIsDeletedFalseAndIsPublicFalse(roomPrivilege.getRoom().getRoomId());

            if(privateRoom != null)
            {
                rooms.add(privateRoom);
            }
        }

        return rooms;
    }

    public Room createOrUpdateRoom(Room room)
    {
        User user = mainGateway.getUserFromSecurity();
        RoomPrivilege roomPrivilege = new RoomPrivilege();

        if(room.getRoomId() == null)
        {
            roomPrivilege.setUser(user);
            roomPrivilege.setRoom(room);
            roomPrivilege.setType(RoomPrivilegeTypes.admin);

            roomRepository.save(room);
            roomPrivilegeRepository.save(roomPrivilege);
        }
        else
        {
            roomRepository.save(room);
        }

        return room;
    }

    public Room getRoom(int roomId)
    {
        Room room = roomRepository.findByRoomIdAndIsDeletedFalse(roomId);

        if(room == null)
        {
            System.out.println("The room you are trying to access does not exist or is deleted");
            return null;
        }

        if(!room.isPublic())
        {
            List<Integer> authorizedUser = Arrays.asList(RoomPrivilegeTypes.admin, RoomPrivilegeTypes.moderator, RoomPrivilegeTypes.member);

            if(!privilegeService.userHasRequiredPrivilege(authorizedUser, room.getRoomId()))
            {
                System.out.println("Cannot access a privcate room that you are not member of");
                return null;
            }
        }

        return room;
    }

    public List<Room> searchRoom(String query)
    {
        return roomRepository.findByNameContainingAndIsDeletedFalse(query);
    }

    public Room deleteRoom(int roomId)
    {
        Room roomToDelete = roomRepository.findByRoomIdAndIsDeletedFalse(roomId);

        if(roomToDelete == null || roomToDelete.isDeleted())
        {
            System.out.println("Cannot delete a room that does not exist");
            return null;
        }

        roomToDelete.setDeleted(true);

        return roomRepository.save(roomToDelete);
    }
}
