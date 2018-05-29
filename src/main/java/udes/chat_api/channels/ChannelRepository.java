package udes.chat_api.channels;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import udes.chat_api.users.User;

import java.util.List;

@Repository
public interface ChannelRepository extends CrudRepository<Channel, Long>
{
    Channel findByChannelId(int channelId);

    List<Channel> findByRoomRoomIdOrderByNameAsc(int roomId);
}
