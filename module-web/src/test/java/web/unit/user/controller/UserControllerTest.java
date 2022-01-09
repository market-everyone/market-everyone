package web.unit.user.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import web.unit.common.TestWithSecurity;
import web.user.controller.UserController;
import web.user.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = {
    UserController.class
})
class UserControllerTest extends TestWithSecurity {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("API - 회원가입 / 성공")
    @Test
    void signup_Success() throws Exception {
        //given
        MultiValueMap<String, String> userSignUpRequest = createSignUpRequest();
        given(userService.checkUserSignUpRequestValidation(any(), any())).willReturn(true);

        //when
        ResultActions perform = mockMvc.perform(post("/users/signup")
                .params(userSignUpRequest));

        //then
        perform.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:login"));
    }

    @DisplayName("API - 회원가입 / 실패")
    @Test
    void signup_Fail() throws Exception {
        //given
        MultiValueMap<String, String> userSignUpRequest = createSignUpRequest();
        given(userService.checkUserSignUpRequestValidation(any(), any())).willReturn(false);

        //when
        ResultActions perform = mockMvc.perform(post("/users/signup")
                        .params(userSignUpRequest));

        //then
        perform.andExpect(view().name("user/join"));
    }

    private MultiValueMap<String, String> createSignUpRequest() {
        MultiValueMap<String, String> signUpRequest = new LinkedMultiValueMap<>();
        signUpRequest.add("email", "test@gmail.com");
        signUpRequest.add("password", "test1234");
        signUpRequest.add("passwordConfirm", "test1234");
        signUpRequest.add("nickname", "testname");
        return signUpRequest;
    }
}