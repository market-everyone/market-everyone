//package web.security;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import web.user.domain.User;
//import web.user.domain.UserRepository;
//
//@RequiredArgsConstructor
//@Slf4j
//@Service
//public class UserPrincipalService implements UserDetailsService, AuthenticationManager {
//
//    private final UserRepository userRepository;
//
//    @Transactional(readOnly = true)
//    @Override
//    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
//        log.info("UserPrincipalService.loadUserByUsername");
//        User user = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원의 이메일입니다."));
//
//        return new UserPrincipal(user);
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        log.info("UserPrincipalService.loadUserByUsername");
//        User user = userRepository.findByEmail(authentication.getName())
//                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원의 이메일입니다."));
//
//        return new UsernamePasswordAuthenticationToken(new UserPrincipal(user), authentication.getCredentials());
//    }
//}
