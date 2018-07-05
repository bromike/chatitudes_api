package udes.chat_api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import udes.chat_api.users.User;
import udes.chat_api.users.UserRepository;

@Service
public class MainGateway
{
    @Autowired
    private UserRepository userRepository;

    public User getUserFromSecurity()
    {
        String userCip = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findByCip(userCip);
    }
}
