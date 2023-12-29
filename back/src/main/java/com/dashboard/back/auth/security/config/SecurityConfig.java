package com.dashboard.back.auth.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import lombok.RequiredArgsConstructor;

import com.dashboard.back.auth.common.AuthConst;
import com.dashboard.back.auth.common.FinalVariable;
import com.dashboard.back.auth.filter.JwtAuthorizationFilter;
import com.dashboard.back.auth.repository.UserRepository;
import com.dashboard.back.auth.security.handler.AuthLogoutHandler;
import com.dashboard.back.auth.security.handler.AuthWorkHandler;
import com.dashboard.back.auth.security.handler.AuthSuccessHandler;
import com.dashboard.back.auth.service.UserService;
import com.dashboard.back.auth.token.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Value("${jwt.header-name}")
	private String HEADER_NAME;

	@Value("${server.servlet.context-path}")
	private String CONTENXT_PATH;

	@Autowired
	private CorsConfig corsConfig;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthWorkHandler authLogoutWorkHandler;

	@Autowired
	private AuthConst authConst;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 초기화
	private void initialize() {
		System.out.println(authConst.getLOGIN_SUFFIX_URL());
		System.out.println(authConst.getLOGIN_FULL_URL());
		System.out.println(authConst.getLOGIN_PROC_URL());
		System.out.println(authConst.getLOGOUT_FULL_URL());
		System.out.println(authConst.getLOGOUT_PROC_URL());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		this.initialize();

		// MyFilter3에서 Get Method은 제외하고 Post Method만 받기때문에 Get Method을 받을 경우 아래
		// http.addFilterBefore~~을 주석처리하자
		// http.addFilterBefore(new MyFilter3(),
		// SecurityContextPersistenceFilter.class);

		http.csrf().disable();

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().cors()
				.configurationSource(corsConfig.corsFilter()).and().formLogin()
				.loginPage(authConst.getLOGIN_SUFFIX_URL())
				.successHandler(new AuthSuccessHandler(jwtTokenProvider, userService))
				.loginProcessingUrl(FinalVariable.SECURITY_LOGIN_PROC) // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로 채서 대신 로그인해준다.
				// .defaultSuccessUrl("/") // 정상일때 "/" 쪽으로감 => 이거하면 successHandler 안된다.
				.and().httpBasic().disable()
				.addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository, jwtTokenProvider,
						authLogoutWorkHandler)) // AuthenticationManager가 파라메터로 넘겨야 한다.
				.authorizeRequests().antMatchers(FinalVariable.SECURITY_PERMIT_ALL).permitAll()
				.antMatchers("/", "/home", "/index", "/main")
				.access("hasRole('ROLE_USER') or hasRole('ROLE_GUEST') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
				.antMatchers("/api/v1/manager/**").access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
				.antMatchers("/api/v1/admin/**")
				// 아래 role_admin인 일 때만 허용
				//.antMatchers("/api/v1/admin/**", "/swagger-ui.html", "/swagger*/**", "/webjars/**", "/swagger-resources/**")
				.access("hasRole('ROLE_ADMIN')").anyRequest().permitAll().and().logout()
				.logoutUrl(FinalVariable.SECURITY_LOGOUT_URL).logoutSuccessUrl(authConst.getLOGIN_SUFFIX_URL())
				.invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JSESSIONID", HEADER_NAME)
				.permitAll().addLogoutHandler(new AuthLogoutHandler(authLogoutWorkHandler)).and().headers()
				.cacheControl().disable().frameOptions().sameOrigin().httpStrictTransportSecurity().disable();

		// http.addFilterBefore(new JwtAuthenticationFilter(),
		// UsernamePasswordAuthenticationFilter.class);
		// http.addFilterBefore(jwtTokenFilter,
		// UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring();
		web.httpFirewall(defaultHttpFirewall());
	}

	@Bean
	public HttpFirewall defaultHttpFirewall() {
		return new DefaultHttpFirewall();
	}
}
