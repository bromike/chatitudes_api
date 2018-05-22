package udes.chat_api.users;
//package udes.chatitudes_web_api.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String getViewClinic(Model model)
    {
        Iterable<User> users = userRepository.findAll();
        return "";
    }
}
