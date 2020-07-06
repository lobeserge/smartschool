package ub.fet.smartschool.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ub.fet.smartschool.security.jwt.AuthAdminTokenFilter;
import ub.fet.smartschool.security.jwt.AuthEntryPointJwt;
import ub.fet.smartschool.security.jwt.AuthStaffTokenFilter;
import ub.fet.smartschool.security.jwt.AuthTokenFilter;
import ub.fet.smartschool.security.services.AdminDetailsServiceImpl;
import ub.fet.smartschool.security.services.StaffDetailsImpl;
import ub.fet.smartschool.security.services.StaffDetailsServiceImpl;
import ub.fet.smartschool.security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	StaffDetailsServiceImpl staffDetailsService;
	@Autowired
	AdminDetailsServiceImpl adminDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	public AuthAdminTokenFilter authenticationAdminJwtTokenFilter() {
		return new AuthAdminTokenFilter();
	}

	@Bean
	public AuthStaffTokenFilter authenticationStaffJwtTokenFilter() {
		return new AuthStaffTokenFilter();
	}


	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		authenticationManagerBuilder.userDetailsService(staffDetailsService).passwordEncoder(passwordEncoder());
		authenticationManagerBuilder.userDetailsService(adminDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
				"/swagger-ui.html", "/webjars/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
				.and()
				.csrf().disable()
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers("/",
						"/favicon.ico",
						"/**/*.png",
						"/**/*.gif",
						"/**/*.svg",
						"/**/*.jpg",
						"/**/*.html",
						"/**/*.ftl",
						"/**/*.css",
						"/**/*.js").permitAll()
				.antMatchers("/**/api/auth/**").permitAll()
				.antMatchers("/api/test/**").permitAll()
				.antMatchers("/api/**").permitAll()
				.anyRequest().authenticated();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(authenticationAdminJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(authenticationStaffJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
