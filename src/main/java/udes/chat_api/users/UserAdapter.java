package udes.chat_api.users;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAdapter
{
    @Autowired
    private UserRepository userRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public UserDto toDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);

        return userDto;
    }

    public User toEntity(UserDto userDto)
    {
        User user = modelMapper.map(userDto, User.class);

        return user;
    }
}
