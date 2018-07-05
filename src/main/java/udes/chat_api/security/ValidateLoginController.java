package udes.chat_api.security;


import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import udes.chat_api.users.User;
import udes.chat_api.users.UserRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class ValidateLoginController {

    @Autowired
    CasSecurityChatApi.ApiSecurityConfiguration apiSecurityConfiguration;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/validateLogin")
    public AppUserDetails validateLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof CasAssertionAuthenticationToken) {
            try
            {
                return (AppUserDetails) apiSecurityConfiguration.customUserDetailsService().loadUserDetails((CasAssertionAuthenticationToken)auth);
            }
            catch (ClassCastException ex){
                throw ex;
            }
        }
        else if (auth instanceof CasAuthenticationToken){
            try {
                String ticket = auth.getCredentials().toString();
                Assertion assertion = ((CasAuthenticationToken) auth).getAssertion();
                CasAssertionAuthenticationToken token = new CasAssertionAuthenticationToken(assertion, ticket);
                return (AppUserDetails) apiSecurityConfiguration.customUserDetailsService().loadUserDetails(token);
            }
            catch(ClassCastException ex) {
                throw ex;
            }
        }
        else {
            return null;
        }
    }

    @GetMapping("/validateTicket")
    public @ResponseBody AppUserDetails getUserDetails(@RequestParam("ticket") String ticket, @RequestParam("service") String service){
        try {
            Assertion assertion = apiSecurityConfiguration.cas20ServiceTicketValidator().validate(ticket, service);
            CasAssertionAuthenticationToken token = new CasAssertionAuthenticationToken(assertion, ticket);
            SecurityContextHolder.getContext().setAuthentication(token);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            AppUserDetails appUserDetails = (AppUserDetails) apiSecurityConfiguration.customUserDetailsService().loadUserDetails(token);
            if (userRepository.findByCip(appUserDetails.getCip()) == null){
                User newUser = new User();
                newUser.setCip(appUserDetails.getCip());
                newUser.setFirstName(appUserDetails.getFirst_name());
                newUser.setLastName(appUserDetails.getLast_name());
                userRepository.save(newUser);
            }
            auth.setAuthenticated(true); //Security check done !
            return  appUserDetails;
        }catch (TicketValidationException ex){
            System.out.println("Could not validate the ticket");
            return null;
        }
    }
}