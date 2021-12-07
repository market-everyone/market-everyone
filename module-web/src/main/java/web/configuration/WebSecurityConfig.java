package web.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import web.security.CustomAccessDeniedHandler;
import web.security.CustomAuthenticationEntryPoint;
import web.security.CustomLogoutFilter;
import web.security.CustomAuthenticationFilter;
import web.security.oauth2.CustomAuthenticationFailureHandler;
import web.security.oauth2.CustomAuthenticationSuccessHandler;
import web.security.oauth2.Oauth2UserPrincipalService;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";

    private final Oauth2UserPrincipalService oauth2UserPrincipalService;

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    public WebSecurityConfig(Oauth2UserPrincipalService oauth2UserPrincipalService,
                             CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
                             CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        this.oauth2UserPrincipalService = oauth2UserPrincipalService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
    }

    @Bean
    public CustomAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
        customAuthenticationFilter.setFilterProcessesUrl("/api/users/login");
        customAuthenticationFilter.setUsernameParameter("email");
        customAuthenticationFilter.setPasswordParameter("password");
        customAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return customAuthenticationFilter;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .antMatchers("/scss/**", "/vendor/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /* h2 데이터베이스를 위한 설정 */
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable()
                /*                       */
                .and()
                .formLogin()
                .disable()
                .httpBasic()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/admin/**")
                .hasRole(ADMIN)
                .antMatchers(HttpMethod.GET, "/admin/**")
                .hasRole(ADMIN)
                .antMatchers(HttpMethod.GET, "/users/mypage")
                .hasAnyRole(ADMIN, USER)
                .anyRequest()
                .permitAll()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
                .and()
                .userInfoEndpoint()
                .userService(oauth2UserPrincipalService)
                .and()
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler);

        http
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(logoutFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    private CustomLogoutFilter logoutFilter() {
        return new CustomLogoutFilter(
            (req, res, auth) -> res.sendRedirect("/"),
            (req, res, auth) -> SecurityContextHolder.clearContext()
        );
    }
}
