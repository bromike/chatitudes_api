package udes.chat_api.security;

import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;

public class CustomUserDetailsService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {
    public CustomUserDetailsService() {
        super();
    }

    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken casAssertionAuthenticationToken) throws UsernameNotFoundException {
        Map<String, Object> properties = casAssertionAuthenticationToken.getAssertion().getPrincipal().getAttributes();
        AppUserDetails userDetails = new AppUserDetails();
        userDetails.setCip((String)properties.get("cip"));
        userDetails.setCourriel((String)properties.get("courriel"));
        userDetails.setFirst_name((String)properties.get("prenom"));
        userDetails.setLast_name((String)properties.get("nomFamille"));
        return userDetails;
    }
}
