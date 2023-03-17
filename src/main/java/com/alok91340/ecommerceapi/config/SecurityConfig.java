package com.alok91340.ecommerceapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.alok91340.ecommerceapi.security.CustomUserDetailsService;
import com.alok91340.ecommerceapi.security.JwtAuthenticationEntryPoint;
import com.alok91340.ecommerceapi.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
// @EnableSwagger2
/***
 * global security is used for enable security at method level for example
 * permitting get methods
 * Ex: PreAuthorize("hasRole('ADMIN')")
 ***/
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    public static final String[] PUBLIC_URLS = {
            "/v3/api-docs",
            "/v2/api-docs",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/api/v*/auth/**"
    };

    @Autowired(required = false)
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                /*-------------------------JWT Starts------------------------------*/
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                /*-------------------------JWT ends------------------------------*/
                .and()
                .authorizeHttpRequests()
                // to permit all get request and secure post put and delete methods
                .requestMatchers("/v3/api-docs").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                .requestMatchers(PUBLIC_URLS).permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v*/contact/**").permitAll()
                .anyRequest()
                .authenticated();

        /**
         * Basic auth used before JWT implementation
         * .and()
         * .httpBasic();
         **/
        http
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // Jwt auth filter method
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    // In memory Auth
    /**
     * @Override
     * @Bean
     *       protected UserDetailsService userDetailsService() {
     *       UserDetails user =
     *       User.builder().username("customer").password(passwordEncoder().encode("customer")).roles("USER").build();
     *       UserDetails admin =
     *       User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
     * 
     *       return new InMemoryUserDetailsManager(user, admin);
     *       }
     **/

    // DB Auth
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // User authentication manager bean
    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
