package br.com.Sparta.SpartaSalesManager.security.config;

import br.com.Sparta.SpartaSalesManager.security.filter.JWTAuthenticationFilter;
import br.com.Sparta.SpartaSalesManager.security.filter.JWTAuthorizationFilter;
import br.com.Sparta.SpartaSalesManager.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
        http.cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/*/administrador/**").hasRole("ADMINISTRADOR")
                .antMatchers("/*/vendedor/**").hasRole("VENDEDOR")
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), customUserDetailsService));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}