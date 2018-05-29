package udes.chat_api.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import udes.chat_api.gateway.UserGateway;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController
{
    @Autowired
    private UserGateway userGateway;
    @Autowired
    private UserAdapter userAdapter;

    @GetMapping("/")
    List<UserDto> getUsers()
    {
        List<User> users = userGateway.getUsers();
        return users.stream()
                .map(user -> userAdapter.toDto(user))
                .collect(Collectors.toList());
    }
}
