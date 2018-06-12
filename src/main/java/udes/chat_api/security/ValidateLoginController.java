package udes.chat_api.security;


import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class ValidateLoginController {

    @GetMapping("/validateLogin")
    public String getFoosBySimplePath() {
        return "Get some Foos";
    }
}
