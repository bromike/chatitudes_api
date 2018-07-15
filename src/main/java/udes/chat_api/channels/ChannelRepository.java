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

    Channel findByChannelIdAndIsDeletedFalse(int channelId);

    List<Channel> findByNameContainingAndIsDeletedFalse(String name);

    List<Channel> findByIsDeletedFalseAndRoomRoomIdOrderByNameAsc(int roomId);

    List<Channel> findByIsDeletedFalseAndIsPublicTrueAndRoomRoomId(int roomId);
    Channel findByIsDeletedFalseAndIsPublicFalseAndChannelId(int channelId);

    @Transactional
    void deleteChannelByChannelId(int channelId);
}
