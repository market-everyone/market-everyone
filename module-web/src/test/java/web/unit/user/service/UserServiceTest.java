package web.unit.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import web.user.controller.dto.request.UserSignUpRequest;
import web.user.domain.User;
import web.user.domain.UserRepository;
import web.user.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Model model;

    @Test
    void save() {
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void checkUserSignUpRequestValidation() {
        //given
        UserSignUpRequest userSignUpRequest = createUserSignUpRequest();
        given(userRepository.existsByEmail(userSignUpRequest.getEmail())).willReturn(true);
        given(userRepository.existsByNickname(userSignUpRequest.getNickname())).willReturn(true);

        //when
        boolean result = userService.checkUserSignUpRequestValidation(userSignUpRequest, model);

        //then
        assertThat(result).isFalse();
    }

    private UserSignUpRequest createUserSignUpRequest() {
        return UserSignUpRequest.builder()
                .email("test@gmail.com")
                .password("test1234")
                .passwordConfirm("test1234")
                .nickname("testname")
                .build();
    }

    @Test
    void registerUser(UserSignUpRequest userSignUpRequest) {
//        PlatformTransactionManager transactionManager = new JdbcTransactionManager();
//        PlatformTransactionManager transactionManager = new JpaTransactionManager();
        PlatformTransactionManager transactionManager = new HibernateTransactionManager();

        TransactionStatus transactionStatus = transactionManager
                                    .getTransaction(new DefaultTransactionDefinition());

        try {
            if (isEmailDuplicated(userSignUpRequest)) {
                userRepository.save(userSignUpRequest.toUser());
            }

            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            transactionManager.rollback(transactionStatus);

        }
    }

    private boolean isEmailDuplicated(UserSignUpRequest userSignUpRequest) {
        return true;
    }
}