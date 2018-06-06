package udes.chat_api.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;
import udes.chat_api.gateway.UserGateway;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController
{
    @Autowired
    private UserGateway userGateway;
    @Autowired
    private UserAdapter userAdapter;

    @GetMapping("/user")
    public List<UserDto> getUsers()
    {
        List<User> users = userGateway.getUsers();

        return users.stream()
                .map(user -> userAdapter.toDto(user))
                .collect(Collectors.toList());
    }
}
