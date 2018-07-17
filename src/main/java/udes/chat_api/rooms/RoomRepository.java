package udes.chat_api.rooms;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long>
{
    @Override
    List<Room> findAll();

    List<Room> findByIsDeletedFalseAndIsPublicTrue();

    Room findByRoomIdAndIsDeletedFalse(int roomId);

    Room findByRoomIdAndIsDeletedFalseAndIsPublicFalse(int roomId);

    List<Room> findByNameContainingAndIsDeletedFalse(String name);

    @Transactional
    void deleteByRoomId(int roomId);
}
