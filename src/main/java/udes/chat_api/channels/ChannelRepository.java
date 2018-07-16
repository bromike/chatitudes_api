package udes.chat_api.channels;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends CrudRepository<Channel, Long>
{
    @Override
    List<Channel> findAll();

    List<Channel> findByIsDeletedFalseAndRoomRoomId(int roomId);

    Channel findByChannelIdAndIsDeletedFalse(int channelId);
}
