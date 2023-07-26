package com.course.biblioteca.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) -> authorize
                		.requestMatchers("/register/**").permitAll()
                        .requestMatchers("/index").permitAll()
                        .requestMatchers("/view/emprestimo/cadastrar").hasRole("ADMIN")
                        .requestMatchers("/view/emprestimo/atualizar").hasRole("ADMIN")
                        .requestMatchers("/view/emprestimo/deletar").hasRole("ADMIN")
                        .requestMatchers("/view/emprestimo/lista").hasRole("USER")
                        .requestMatchers("/view/livro/cadastrar").hasRole("ADMIN")
                        .requestMatchers("/view/livro/atualizar").hasRole("ADMIN")
                        .requestMatchers("/view/livro/deletar").hasRole("ADMIN")
						.requestMatchers("/view/livro/lista").hasRole("USER")
						.requestMatchers("/view/usuario/cadastrar").hasRole("ADMIN")
                        .requestMatchers("/view/usuario/atualizar").hasRole("ADMIN")
                        .requestMatchers("/view/usuario/deletar").hasRole("ADMIN")
						.requestMatchers("/view/usuario/lista").hasRole("USER")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/session")
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll())
                .build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}


