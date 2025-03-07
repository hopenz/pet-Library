package ru.hopenz.petLibrary.config;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@Configuration
//@EnableWebMvc
public class SpringSecurityConfig {

//    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((authz) -> authz
//                        .requestMatchers("/registration", "/login", "/logout")
//                        .permitAll()
//                        .requestMatchers("/swagger-ui/**").hasRole("ADMIN")
//                        .anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults());
//        return http.build();
//    }
}
