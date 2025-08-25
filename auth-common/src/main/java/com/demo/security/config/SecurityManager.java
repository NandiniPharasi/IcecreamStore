package com.demo.security.config;




import com.demo.security.config.client.SecurityServerRestClient;
import com.demo.security.config.dto.UsernameAuthoritiesDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static java.util.Optional.*;
import static java.util.stream.Collectors.toList;

/**
 *    Manages the validation of the token related with a logged user, using the {@link Authentication}
 * to get and fill the required {@link UsernamePasswordAuthenticationToken} used later to know if the
 * user has the correct {@link GrantedAuthority}.
 */

@Component
public class SecurityManager {

    @Autowired
    private final SecurityServerRestClient securityServerRestClient;

    private static final Logger log = LoggerFactory.getLogger(SecurityManager.class);

    

	public SecurityManager(SecurityServerRestClient securityServerRestClient) {
		super();
		this.securityServerRestClient = securityServerRestClient;
	}


	public Optional<Authentication> authenticate(final String authToken) {
        return getAuthenticationInformation(
                authToken
        )
        .map(x -> getFromUsernameAuthoritiesDto(x, authToken));
    }


    /**
     * Using the given token gets the authentication information related with the logged user.
     *
     * @param token
     *    Token (included Http authentication scheme)
     *
     * @return {@link Optional} of {@link UsernameAuthoritiesDto}
     */
    private Optional<UsernameAuthoritiesDto> getAuthenticationInformation(final String token) {
        try {
            return of(
                    securityServerRestClient.checkToken(token, Constants.TOKEN_PREFIX + token)
            );
        } catch (Exception ex) {
            log.error("There was an error trying to validate the authentication token", ex);
            return empty();
        }
    }


    /**
     * Converts a given {@link UsernameAuthoritiesDto} into an {@link UsernamePasswordAuthenticationToken}
     *
     * @param usernameAuthoritiesDto
     *    {@link UsernameAuthoritiesDto} to convert
     *
     * @return {@link UsernamePasswordAuthenticationToken}
     */
    private UsernamePasswordAuthenticationToken getFromUsernameAuthoritiesDto(final UsernameAuthoritiesDto usernameAuthoritiesDto, final String authToken) {
        Collection<? extends GrantedAuthority> authorities = ofNullable(usernameAuthoritiesDto.getAuthorities())
                .map(auth ->
                        auth.stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(toList())
                )
                .orElseGet(ArrayList::new);

        return new UsernamePasswordAuthenticationToken(
                usernameAuthoritiesDto.getUsername(),
                Constants.TOKEN_PREFIX + authToken,
                authorities
        );
    }

}
