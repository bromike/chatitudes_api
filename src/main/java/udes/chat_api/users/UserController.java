package udes.chat_api.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import udes.chat_api.gateway.UserGateway;

@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    private UserGateway userGateway;

    @GetMapping("/")
    Iterable<UserDto> getAllUser(UserDto userDto, Model model)
    {
        Iterable<UserDto> users = userGateway.getAllUser(userDto);

        return users;
    }
}
