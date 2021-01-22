package pl.qubiak.security.SecurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user = User.withDefaultPasswordEncoder()    // tylko do nałuki, bo przechowuje hasła bez szyfrowania
                .username("user1")
                .password("password1")
                .roles("standard")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("user2")
                .password("password2")
                .roles("admin")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()    //  w momencie obsługi żądania wymagamy interakcji
                .antMatchers("/hello").permitAll()     //  hashRole - zależne od roli, autentificatoin - tylko potwierdzenie, permitAll - zezwól wszystkim
                .anyRequest().hasRole("admin")  //  każdy inny request prócz hello wymaga roli admin
                .and()
                .formLogin().permitAll()   //  formularz logowania (czy jest formularz fejestrowania?)
                .and()
                .logout().permitAll();      //  localhost:8080/logout
    }
}
