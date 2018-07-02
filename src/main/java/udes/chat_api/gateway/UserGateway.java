package udes.chat_api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import udes.chat_api.users.User;
import udes.chat_api.users.UserService;

import java.util.List;

@Service
public class UserGateway
{
    @Autowired
    private UserService userService;

    public List<User> getUsers()
    {
        // Check room_privileges
        // Return privilege error if the user does not have the required room_privileges

        return userService.getUsers();
    }
}
