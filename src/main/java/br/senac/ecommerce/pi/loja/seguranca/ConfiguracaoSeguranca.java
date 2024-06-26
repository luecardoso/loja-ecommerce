package br.senac.ecommerce.pi.loja.seguranca;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSeguranca extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new DetalhesUsuario();
	}

	@Bean
	public PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(PasswordEncoder());
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	/* PERMISÃO DE ACESSO AUTORIZADO E COM CARGO ESPECIFICO */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/home/**").permitAll()
				.antMatchers("/js/**", "/fontawesome/**", "/richtext/**", "/webfonts/**", "/error").permitAll()
				.antMatchers("/administrador").hasAnyAuthority("Administrador", "Estoquista")
				.antMatchers("/administrador/usuario/**").hasAuthority("Administrador")
				.antMatchers("/administrador/categoria/**").hasAuthority("Administrador")
				.antMatchers("/administrador/marca/**").hasAuthority("Administrador")
				.antMatchers("/administrador/produto/**").hasAnyAuthority("Administrador", "Estoquista")
				.antMatchers("/administrador/produto/salvar/**").hasAnyAuthority("Administrador", "Estoquista")
				.antMatchers("/administrador/produto/editar/**").hasAnyAuthority("Administrador", "Estoquista")
				.antMatchers("/administrador/produto/pagina/**").hasAnyAuthority("Administrador", "Estoquista")
				.antMatchers("/administrador/produto/mostrarImagem/**").hasAnyAuthority("Administrador", "Estoquista")
				.anyRequest().authenticated()
				.and().formLogin()
				.defaultSuccessUrl("/home")
				.loginPage("/login")
				.usernameParameter("email").permitAll()
				.and().logout().permitAll();
		// .and()
		// .exceptionHandling().accessDeniedPage("/acesso-negado");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {

		web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**", "/home/**");
	}
}
