package udes.chat_api.channels;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ChannelRepository extends CrudRepository<Channel, Long>
{
    @Override
    List<Channel> findAll();

    Channel findByChannelId(int channelId);

    List<Channel> findByNameContaining(String name);

    List<Channel> findByRoomRoomIdOrderByNameAsc(int roomId);

    @Transactional
    void deleteChannelByChannelId(int channelId);
}
