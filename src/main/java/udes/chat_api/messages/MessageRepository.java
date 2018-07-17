package udes.chat_api.messages;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>
{
    @Override
    List<Message> findAll();

    Message findByMessageId(int messageId);

    Message findByMessageIdAndIsDeletedFalse(int messageId);

    List<Message> findByIsDeletedFalseAndChannelChannelId(int channelId);
}
