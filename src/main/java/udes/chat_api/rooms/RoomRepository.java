package udes.chat_api.rooms;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import udes.chat_api.channels.Channel;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long>
{
    Room findByRoomId(int roomId);

    List<Room> findByNameContaining(String name);
}
