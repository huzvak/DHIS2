package org.dhis.security;

import org.dhis.user.User;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class DefaultLoginService
    implements LoginService
{
    /**
     * Cache for login attempts where usernames are keys and login attempts are values.
     */
    private final Cache<String, Integer> USERNAME_LOGIN_ATTEMPTS_CACHE = Caffeine.newBuilder()
        .build(); //TODO Hint: you might want to provide additional configuration of this cache
    
    @Override
    public void registerAuthenticationFailure( AuthenticationEvent event )
    {
        // TODO Implement this method        
    }

    @Override
    public void registerAuthenticationSuccess( AuthenticationEvent event )
    {
        // TODO Implement this method        
    }

    @Override
    public boolean isBlocked( User user )
    {
        // TODO Implement this method        
        return false;
    }
}
