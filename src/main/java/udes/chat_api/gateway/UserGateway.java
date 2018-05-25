package udes.chat_api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import udes.chat_api.users.UserDto;
import udes.chat_api.users.UserService;

@Service
public class UserGateway
{
    @Autowired
    private UserService userService;

    public Iterable<UserDto> getAllUser(UserDto userDto)
    {
        //Check privileges with userDto

        return userService.getAllUser();
    }
}
