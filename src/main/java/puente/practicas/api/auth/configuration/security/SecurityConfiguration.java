package puente.practicas.api.auth.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import puente.practicas.api.auth.configuration.security.filter.JwtAuthorizationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(http -> {
                    http.requestMatchers(HttpMethod.POST, "/auth/login", "/auth/signup").permitAll();

                    http.requestMatchers("/recruiter-profiles/**").hasRole("RECRUITER");

                    http.requestMatchers(HttpMethod.POST, "/student-profiles").hasRole("STUDENT");
                    http.requestMatchers(HttpMethod.GET, "/student-profiles/me").hasRole("STUDENT");
                    http.requestMatchers(HttpMethod.GET, "/student-profiles/{userEmail}").permitAll();

                    http.requestMatchers(HttpMethod.POST, "/companies").hasRole("RECRUITER");
                    http.requestMatchers(HttpMethod.GET, "/companies").hasRole("RECRUITER");
                    http.requestMatchers(HttpMethod.GET, "/companies/{companyId}").permitAll();

                    http.requestMatchers(HttpMethod.POST, "/applications").hasRole("STUDENT");
                    http.requestMatchers(HttpMethod.PATCH, "/applications").hasRole("STUDENT");
                    http.requestMatchers(HttpMethod.GET, "/applications/me").hasRole("STUDENT");
                    http.requestMatchers(HttpMethod.PATCH, "/applications/status").hasRole("RECRUITER");
                    http.requestMatchers(HttpMethod.GET, "/applications").hasRole("RECRUITER");
                    http.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
