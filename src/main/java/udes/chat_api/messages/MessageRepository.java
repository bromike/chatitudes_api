package udes.chat_api.messages;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import udes.chat_api.channels.Channel;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>
{

    @Override
    List<Message> findAll();

    Message findByMessageId(Integer messageId);

    List<Message> findByChannelChannelId(int channelId);

    @Transactional
    void deleteByMessageId(int messageId);

    List<Message> findAllByAuthorCip(String cip);
}
