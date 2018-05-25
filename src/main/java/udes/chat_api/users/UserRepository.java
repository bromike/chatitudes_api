package udes.chat_api.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>
{
    User findByCip(String cip);
}
