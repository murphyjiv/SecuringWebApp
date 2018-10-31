package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Configuration
	class Security extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

		// from WebSecurityConfigurerAdapter
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				// permit images and css only (js would be appropriate too if we had any)
				.authorizeRequests()
					.antMatchers("/images/**", "/css/**").permitAll()
					.anyRequest().authenticated()
				// indicate that the login form is found at /login
				.and()
					.formLogin().loginPage("/login").permitAll()
				// default login url is /logout
				.and()
					.logout().permitAll();
		}

		// from WebMvcConfigurer
		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			// this maps login url (/login) to a view called "login" which
			// will serve up the file in src/main/resources/templates/login.html
			registry.addViewController("/login").setViewName("login");
		}
	}

}
