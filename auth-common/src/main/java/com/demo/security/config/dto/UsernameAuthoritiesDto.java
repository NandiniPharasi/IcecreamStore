package com.demo.security.config.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

/**
 * Class used to receive the authorization information related with logged users
 */

public class UsernameAuthoritiesDto {

    @JsonProperty("user_name")
    private String username;
    private Set<String> authorities;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Set<String> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}
	
	public UsernameAuthoritiesDto(String username, Set<String> authorities) {
		super();
		this.username = username;
		this.authorities = authorities;
	}
	
	public UsernameAuthoritiesDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		return Objects.hash(username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsernameAuthoritiesDto other = (UsernameAuthoritiesDto) obj;
		return Objects.equals(username, other.username);
	}

	
    
}
