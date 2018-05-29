package udes.chat_api.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.channels.Channel;

import java.util.List;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers()
    {
        List<User> users = (List<User>) userRepository.findAll();

        return users;
    }
}
