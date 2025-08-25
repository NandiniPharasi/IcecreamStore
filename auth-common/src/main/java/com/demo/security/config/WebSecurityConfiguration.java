package com.demo.security.config;



import com.demo.security.config.filter.SecurityFilter;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;


@Configuration
@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {

    private final String SPRING_ACTUATOR_PATH = "/actuator";
    private final String ALLOW_ALL_ENDPOINTS = "/**";

    @Value("${security.auth.bypassUrls:}")
    private String authBypassUrls;

    private static final Logger log = LoggerFactory.getLogger(WebSecurityConfiguration.class);

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                // Make sure we use stateless session; session won't be used to store user's state
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Handle an authorized attempts
                .exceptionHandling().authenticationEntryPoint(
                        (req, rsp, e) ->
                                rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                )
                .and()
                .authorizeRequests()
                // List of services do not require authentication
                .antMatchers(OPTIONS).permitAll()
                .antMatchers(
                        GET,
                        allowedGetEndpoints()
                ).permitAll()
                // Any other request must be authenticated
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                 .build();
    }


    private String[] allowedGetEndpoints() {

        List<String> urls = new ArrayList<>();
        urls.add(SPRING_ACTUATOR_PATH + ALLOW_ALL_ENDPOINTS);

        if(!StringUtils.isEmpty(authBypassUrls)) {
            urls.addAll(Arrays.asList(authBypassUrls.split(",")));
        }


        String[] urlsArray = urls.toArray(new String[0]);
		log.info("bypassing urls: {} {}", urlsArray, new Object());
        return urlsArray;
    }

}
