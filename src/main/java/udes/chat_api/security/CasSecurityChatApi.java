package udes.chat_api.security;

import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class CasSecurityChatApi{
    @Configuration
    static class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Bean
        public ServiceProperties serviceProperties(){
            ServiceProperties serviceProperties = new ServiceProperties();
            serviceProperties.setService("http://localhost:8080/login/cas");
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
        public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
            Cas20ServiceTicketValidator ticketValidator = new Cas20ServiceTicketValidator("https://cas.usherbrooke.ca/");
            return ticketValidator;
        }

        @Bean
         public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
            CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
            casAuthenticationEntryPoint.setLoginUrl("https://cas.usherbrooke.ca/login");
            casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
            return casAuthenticationEntryPoint;
        }

        @Bean
        public AuthenticationUserDetailsService<CasAssertionAuthenticationToken> customUserDetailsService() {
            return new CustomUserDetailsService();
        }

        @Bean
        public CasAuthenticationProvider casAuthenticationProvider(){
            CasAuthenticationProvider cap = new CasAuthenticationProvider();
            cap.setTicketValidator(cas20ServiceTicketValidator());
            cap.setServiceProperties(serviceProperties());
            cap.setKey("casChattitudesAPI");
            cap.setAuthenticationUserDetailsService(customUserDetailsService());
            return cap;
        }

        @Override
        protected void configure(
                AuthenticationManagerBuilder auth) throws Exception {

            auth.authenticationProvider(casAuthenticationProvider());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().and().authenticationProvider(casAuthenticationProvider()).
                    exceptionHandling().
                    authenticationEntryPoint(casAuthenticationEntryPoint()).
                    and().
                    addFilter(casAuthenticationFilter()).
                    authorizeRequests().
                    antMatchers("/validateTicket","/validateLogin", "/login/cas").
                    permitAll().
                    anyRequest().
                    authenticated().and().csrf().disable();

            }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Arrays.asList("*"));
            configuration.setAllowedMethods(Arrays.asList("*"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(Arrays.asList("*"));
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
    }
}
