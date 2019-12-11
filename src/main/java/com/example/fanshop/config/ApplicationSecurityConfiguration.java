package com.example.fanshop.config;

import com.example.fanshop.domain.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    public ApplicationSecurityConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
//                .addFilterAfter(siteminderFilter(), RequestHeaderAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/js/**", "/css/**", "/img/**").permitAll()
                .antMatchers("/", "/users/register", "/users/login").anonymous()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/users/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/home")
                .and()
                .logout()
                .logoutSuccessUrl("/");
    }

//    @Bean(name = "siteminderFilter")
//    public RequestHeaderAuthenticationFilter siteminderFilter() throws Exception {
//        RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter = new RequestHeaderAuthenticationFilter();
//        requestHeaderAuthenticationFilter.setPrincipalRequestHeader("SM_USER");
//        requestHeaderAuthenticationFilter.setAuthenticationManager(authenticationManager());
//        return requestHeaderAuthenticationFilter;
//    }
//    @Bean
//    @Override
//    protected AuthenticationManager authenticationManager() throws Exception {
//        final List<AuthenticationProvider> providers = new ArrayList<>(1);
//        providers.add(preauthAuthProvider());
//        return new ProviderManager(providers);
//    }
//    @Bean(name = "preAuthProvider")
//    PreAuthenticatedAuthenticationProvider preauthAuthProvider() throws Exception {
//        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
//        provider.setPreAuthenticatedUserDetailsService(userDetailsServiceWrapper());
//        return provider;
//    }
//    @Bean
//    UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> userDetailsServiceWrapper() throws Exception {
//        UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = new UserDetailsByNameServiceWrapper<>();
//        wrapper.setUserDetailsService(this.userService);
//        return wrapper;
//    }
}

