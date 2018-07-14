package udes.chat_api.privileges;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomPrivilegeRepository extends CrudRepository<RoomPrivilege, Long>
{

    @Override
    List<RoomPrivilege> findAll();

    List<RoomPrivilege> findByUserCip(String cip);

    List<RoomPrivilege> findByRoomRoomId(int roomId);

    List<RoomPrivilege> findByRoomRoomIdAndType(int roomId, int type);

    RoomPrivilege findByUserCipAndRoomRoomId(String cip, int roomId);
}
