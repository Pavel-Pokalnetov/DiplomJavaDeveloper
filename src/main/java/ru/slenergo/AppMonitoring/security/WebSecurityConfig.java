package ru.slenergo.AppMonitoring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Конфигурация безопасности веб-приложения.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * Конфигурация цепочки фильтров безопасности.
     *
     * @param http объект HttpSecurity для настройки безопасности
     * @return цепочка фильтров безопасности
     * @throws Exception если произошла ошибка при настройке безопасности
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/","/js/**","/css/**","/index.html",
                                "/main/vos5",
                                "/main/vos15",
                                "/report/**",
                                "/history",
                                "/history/**"
                        ).permitAll()
                        .requestMatchers("/input/vos5","/update/vos5").hasAnyRole("VOS5", "ADMIN")
                        .requestMatchers("/input/vos15","/update/vos15").hasAnyRole("VOS15", "ADMIN")
                        .anyRequest().authenticated()
                )
                
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    /**
     * Создает сервис пользователей с предустановленными пользователями.
     *
     * @return сервис пользователей с предустановленными пользователями
     */
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withDefaultPasswordEncoder()
                .username("vos5000")
                .password("123456")
                .roles("VOS5")
                .build());

        inMemoryUserDetailsManager.createUser(User.withDefaultPasswordEncoder()
                .username("vos15000")
                .password("123456")
                .roles("VOS15")
                .build());

        inMemoryUserDetailsManager.createUser(User.withDefaultPasswordEncoder()
                .username("admin")
                .password("Admin12345")
                .roles("ADMIN")
                .build());


        return inMemoryUserDetailsManager;
    }

}
