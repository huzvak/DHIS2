package org.dhis.security;

import java.util.concurrent.TimeUnit;

import org.dhis.user.User;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

public class DefaultLoginService
		implements LoginService
{
	/**
	 * Cache for login attempts where usernames are keys and login attempts are values.
	 */
	private final LoadingCache<String, Integer> USERNAME_LOGIN_ATTEMPTS_CACHE;

	public DefaultLoginService(LoadingCache<String, Integer> cache) {
		USERNAME_LOGIN_ATTEMPTS_CACHE = cache;
	}

	@Override
	public void registerAuthenticationFailure(AuthenticationEvent event)
	{
		USERNAME_LOGIN_ATTEMPTS_CACHE.put(
				event.getUsername(),
				USERNAME_LOGIN_ATTEMPTS_CACHE.get(event.getUsername()) + 1);
	}

	@Override
	public void registerAuthenticationSuccess(AuthenticationEvent event)
	{
		USERNAME_LOGIN_ATTEMPTS_CACHE.invalidate(event.getUsername());
	}

	@Override
	public boolean isBlocked(User user)
	{
		return USERNAME_LOGIN_ATTEMPTS_CACHE.get(user.getUsername()) >= 5;
	}

	public static void main(String[] args) {
		LoadingCache<String, Integer> cache = Caffeine.newBuilder()
				.maximumSize(1_000)
				.expireAfterWrite(1, TimeUnit.HOURS)
				.build(key -> 0);
		DefaultLoginService service = new DefaultLoginService(cache);
	}
}
