package web.unit.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import web.unit.common.TestWithSecurity;
import web.user.controller.UserController;
import web.user.controller.dto.request.UserSignUpRequest;
import web.user.service.UserService;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {
    UserController.class
})
class UserControllerTest extends TestWithSecurity {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("API - 회원가입 / 성공")
    @Test
    void signup_Success() throws Exception {
        //given
        UserSignUpRequest userSignUpRequest = createUserSignUpRequest();

        //when
        ResultActions perform = mockMvc.perform(post("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userSignUpRequest)));

        //then
        perform.andExpect(status().isOk());
    }

    @DisplayName("API - 회원가입 / 실패(이메일 중복)")
    @Test
    void signup_DuplicateEmail_Fail() throws Exception {
        //given
        UserSignUpRequest userSignUpRequest = createUserSignUpRequest();
        given(userRepository.existsByEmail(userSignUpRequest.getEmail())).willReturn(true);

        //when
        ResultActions perform = mockMvc.perform(post("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userSignUpRequest)));

        //then
        perform.andDo(print());
    }

    private UserSignUpRequest createUserSignUpRequest() {
        return UserSignUpRequest.builder()
                .email("mk2e@gmail.com")
                .password("test1234")
                .passwordConfirm("test1234")
                .nickname("mk2e")
                .build();
    }
}