package web.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import web.security.*;
import web.security.oauth2.CustomAuthenticationFailureHandler;
import web.security.oauth2.CustomAuthenticationSuccessHandler;
import web.security.oauth2.Oauth2UserPrincipalService;
import web.seller.domain.Seller;
import web.seller.domain.SellerRepository;
import web.user.domain.User;
import web.user.domain.UserRepository;

import java.util.ArrayList;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String USER = "USER";
    private static final String SELLER = "SELLER";
    private static final String ADMIN = "ADMIN";

    private final Oauth2UserPrincipalService oauth2UserPrincipalService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;

    @Bean
    public CustomAuthenticationFilter userAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
        customAuthenticationFilter.setFilterProcessesUrl("/api/users/login");
        customAuthenticationFilter.setUsernameParameter("email");
        customAuthenticationFilter.setPasswordParameter("password");
        customAuthenticationFilter.setAuthenticationManager(authentication -> {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원의 이메일입니다."));

            if (!new BCryptPasswordEncoder().matches((CharSequence) authentication.getCredentials(), user.getPassword())) {
                throw new BadCredentialsException("비밀번호가 틀렸습니다.");
            }

            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            return new UsernamePasswordAuthenticationToken(new UserPrincipal(user),
                    authentication.getCredentials(), authorities);
        });
        return customAuthenticationFilter;
    }

    @Bean
    public CustomAuthenticationFilter sellerAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
        customAuthenticationFilter.setFilterProcessesUrl("/api/sellers/login");
        customAuthenticationFilter.setUsernameParameter("email");
        customAuthenticationFilter.setPasswordParameter("password");
        customAuthenticationFilter.setAuthenticationManager(authentication -> {
            Seller seller = sellerRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원의 이메일입니다."));

            if (!new BCryptPasswordEncoder().matches((CharSequence) authentication.getCredentials(), seller.getPassword())) {
                throw new BadCredentialsException("비밀번호가 틀렸습니다.");
            }

            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));

            return new UsernamePasswordAuthenticationToken(new SellerPrincipal(seller),
                    authentication.getCredentials(), authorities);
        });
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
                .hasAnyRole(ADMIN, SELLER)
                .antMatchers(HttpMethod.GET, "/admin/**")
                .hasAnyRole(ADMIN, SELLER)
                .antMatchers(HttpMethod.GET, "/users/mypage")
                .hasAnyRole(ADMIN, SELLER, USER)
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

        http.addFilterBefore(logoutFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    private CustomLogoutFilter logoutFilter() {
        return new CustomLogoutFilter(
            (req, res, auth) -> res.sendRedirect("/"),
            (req, res, auth) -> SecurityContextHolder.clearContext()
        );
    }
}
