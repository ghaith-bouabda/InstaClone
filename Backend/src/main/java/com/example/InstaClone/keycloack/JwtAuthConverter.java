package com.example.InstaClone.keycloack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
            new JwtGrantedAuthoritiesConverter();

    @Value("${jwt.auth.converter.principale-attribute}")
    private  String PrincipaleAttribute;

    @Value("${jwt.auth.converter.resource-id}")
    private  String resourceId;


    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities= Stream.concat(
                        jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                             extractResourceRoles(jwt).stream()
        )
                .collect(Collectors.toSet());

        return new JwtAuthenticationToken(jwt
                ,authorities
                ,getPrincipaleClaimName(jwt)
                );
    }

    private String getPrincipaleClaimName(Jwt jwt) {
        String claimName= JwtClaimNames.SUB;
        if(PrincipaleAttribute!=null){
            claimName=PrincipaleAttribute;
        }
        return jwt.getClaim(claimName);
    }

    private Collection<?extends GrantedAuthority> extractResourceRoles(Jwt jwt) {

        Map<String, Object> resourceAcess;
        Map<String, Object> resource;
    Collection<String> resourceRoles;


        if (jwt.getClaim("resource_acess")== null){
            return Set.of();
        }

    resourceAcess = jwt.getClaim("resource_access");

        if    (resourceAcess.get(resourceId)== null){
            return Set.of();
        }
    resource = (Map<String, Object>) resourceAcess.get(resourceId);

    resourceRoles=(Collection<String>) resource.get("roles");

        return resourceRoles
                .stream()
                .map(role->new SimpleGrantedAuthority("ROLE_"+ role))
                .collect(Collectors.toSet());
    }

}
