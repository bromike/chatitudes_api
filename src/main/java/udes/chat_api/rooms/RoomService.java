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

    public Room createRoom(Room room)
    {
        User user = mainGateway.getUserFromSecurity();

        RoomPrivilege roomPrivilege = new RoomPrivilege();
        roomPrivilege.setUser(user);
        roomPrivilege.setRoom(room);
        roomPrivilege.setType(RoomPrivilegeTypes.admin);

        roomRepository.save(room);
        roomPrivilegeRepository.save(roomPrivilege);

        return room;
    }

    public Room getRoom(int roomId)
    {
        User user = mainGateway.getUserFromSecurity();
        Room room = roomRepository.findByRoomIdAndIsDeletedFalse(roomId);

        if(!room.isPublic())
        {
            List<Integer> authorizedUser = Arrays.asList(RoomPrivilegeTypes.admin, RoomPrivilegeTypes.moderator, RoomPrivilegeTypes.member);

            if(!privilegeService.userHasRequiredPrivilege(user.getCip(), authorizedUser, room.getRoomId()))
            {
                return null;
            }
        }

        return room;
    }

    public List<Room> searchRoom(String query)
    {
        return roomRepository.findByNameContainingAndIsDeletedFalse(query);
    }

    public Room updateRoom(Room room)
    {
        Room roomToUpdate = roomRepository.findByRoomIdAndIsDeletedFalse(room.getRoomId());

        if(roomToUpdate == null)
        {
            System.out.println("Cannot update a room that does not exist");
            return null;
        }

        return roomRepository.save(room);
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
