package udes.chat_api.rooms;

import java.util.concurrent.atomic.AtomicLong;

import org.hibernate.Session;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import udes.chat_api.users.User;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class RoomController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/room", method = GET)
    public Room room() {
        return new Room("Le Message de la Requête");
    }

    @RequestMapping(value = "/roomjoin")
    public Room room(@RequestParam(value="user", defaultValue="Default Data") User user) {
        // join room
    }
}