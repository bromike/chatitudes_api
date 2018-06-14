package udes.chat_api.security;


        import org.jasig.cas.client.validation.Assertion;
        import org.jasig.cas.client.validation.TicketValidationException;
        import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
        import org.springframework.security.cas.authentication.CasAuthenticationToken;
        import org.springframework.security.core.Authentication;
        import org.springframework.security.core.context.SecurityContextHolder;
        import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class ValidateLoginController {

    @GetMapping("/validateLogin")
    public AppUserDetails validateLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService();
        if (auth instanceof CasAssertionAuthenticationToken) {
            try
            {
                return (AppUserDetails) customUserDetailsService.loadUserDetails((CasAssertionAuthenticationToken)auth);
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
                return (AppUserDetails) customUserDetailsService.loadUserDetails(token);
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
            Assertion assertion = CasSecurityChatApi.ApiSecurityConfiguration.cas20ServiceTicketValidator().validate(ticket, service);
            CasAssertionAuthenticationToken token = new CasAssertionAuthenticationToken(assertion, ticket);
            SecurityContextHolder.getContext().setAuthentication(token);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            //TODO: Do security check here
            auth.setAuthenticated(true); //Security check done !
            CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService();
            return (AppUserDetails) customUserDetailsService.loadUserDetails(token);
        }catch (TicketValidationException ex){
            System.out.println("Could not validate the ticket");
            return null;
        }
    }
}