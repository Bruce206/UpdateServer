package de.eins.updateserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("${secured-backend.enabled:true}")
	private boolean enabled;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (enabled) {
			http.csrf().disable();

			// @formatter:off
			http.authorizeRequests()
				.antMatchers("/bowerlib/**").permitAll()
				.antMatchers("/theme/**").permitAll()
				.antMatchers("/check/**").permitAll()
				.antMatchers("/download/**").permitAll()
				.antMatchers("/updater/**").permitAll()
				.antMatchers("/api/app/*/image").permitAll()
				.antMatchers("/").permitAll()
				.antMatchers("/**").hasRole("ADMIN")
				
				.anyRequest().authenticated()
				
				.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/admin/", true)
				.permitAll()
				
				.and()
				.logout()
				.logoutSuccessUrl("/")
				.logoutUrl("/logout");
		} else {
			http.csrf().disable();
			http.authorizeRequests().anyRequest().permitAll();
		}
		
		// @formatter:on
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("eins$gmbh").roles("ADMIN");
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}