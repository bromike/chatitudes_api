package udes.chat_api.messages;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import udes.chat_api.channels.Channel;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>
{
    Message findByMessageId(Integer messageId);

    List<Message> findAllByAuthorCip(String cip);
}
