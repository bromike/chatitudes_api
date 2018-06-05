package udes.chat_api.security;

import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;


@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class CasSecurityChatApi{
    @Configuration
    static class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Bean
        public ServiceProperties serviceProperties(){
            ServiceProperties serviceProperties = new ServiceProperties();
            serviceProperties.setService("http://localhost:8080/cas/");
            serviceProperties.setSendRenew(false);
            return serviceProperties;
        }

        @Bean
        public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
            CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
            casAuthenticationFilter.setAuthenticationManager(authenticationManager());
            return casAuthenticationFilter;
        }

        @Bean
        public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
            CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
            casAuthenticationEntryPoint.setLoginUrl("https://cas.usherbrooke.ca/login");
            casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
            return casAuthenticationEntryPoint;
        }

        @Bean
        public CasAuthenticationProvider casAuthenticationProvider(){
            CasAuthenticationProvider cap = new CasAuthenticationProvider();
            cap.setTicketValidator(new Cas20ServiceTicketValidator("https://cas.usherbrooke.ca/validateService"));
            cap.setServiceProperties(serviceProperties());
            cap.setKey("casChattitudesAPI");
            cap.setAuthenticationUserDetailsService(new UserDetailsByNameServiceWrapper(userDetailsService()));
            return cap;
        }

        @Override
        protected void configure(
                AuthenticationManagerBuilder auth) throws Exception {

            auth.authenticationProvider(casAuthenticationProvider());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authenticationProvider(casAuthenticationProvider()).
                    exceptionHandling().
                    authenticationEntryPoint(casAuthenticationEntryPoint()).
                    and().
                    addFilter(casAuthenticationFilter()).
                    authorizeRequests().
                    antMatchers("/", "/cas").
                    permitAll().
                    anyRequest().
                    authenticated();
        }
    }
}
