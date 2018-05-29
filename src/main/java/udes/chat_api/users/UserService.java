package udes.chat_api.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.channels.Channel;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAdapter userAdapter;

    // Peut être fait directement dans le controller, fait ici pour exemple complet du flow
    public Iterable<UserDto> getAllUser()
    {
        Channel channel = new Channel();

        Iterable<User> users = userRepository.findAll();

        Iterable<UserDto> usersDto = userAdapter.toDto(users);

        return usersDto;
    }
}
