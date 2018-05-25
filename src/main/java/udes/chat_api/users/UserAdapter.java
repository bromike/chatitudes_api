package udes.chat_api.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAdapter
{
    @Autowired
    private UserRepository userRepository;

    public User toBo(UserDto userDto)
    {
        User user = userRepository.findByCip(userDto.getCip());

        if(user == null)
        {
            user = new User();
        }

        user.setCip(userDto.getCip());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        return user;
    }

    public UserDto toDto(User user)
    {
        UserDto userDto = new UserDto();

        userDto.setCip(user.getCip());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());

        return userDto;
    }

    public Iterable<UserDto> toDto(Iterable<User> users)
    {
        List<UserDto> userDtos = null;

        for(User user : users)
        {
            userDtos.add(toDto(user));
        }

        return userDtos;
    }
}
