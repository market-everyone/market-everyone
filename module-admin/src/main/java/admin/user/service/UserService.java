package admin.user.service;

import admin.common.dto.PageRequestDTO;
import admin.common.dto.PageResultDTO;
import admin.user.controller.dto.UserRequestDTO;
import admin.user.controller.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.inquiry.domain.InquiryRepository;
import web.user.domain.User;
import web.user.domain.UserRepository;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final InquiryRepository inquiryRepository;

    public PageResultDTO<UserResponseDTO, User> findPageUser(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("id"));
        Page<User> pageUser = userRepository.findAll(pageable);
        Function<User, UserResponseDTO> fn = (UserResponseDTO::new);
        return new PageResultDTO<>(pageUser, fn);
    }

    public UserResponseDTO findUser(Long userNo) {
        User user = userRepository.findById(userNo).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return new UserResponseDTO(user);
    }

    @Transactional
    public void remove(Long userNo) {
        User user = userRepository.findById(userNo).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        inquiryRepository.deleteByUser(user);
        userRepository.deleteById(userNo);
    }

    @Transactional
    public Long update(Long userNo, UserRequestDTO request) {
        User user = userRepository.findById(userNo).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        user.update(request.getName(),
                request.getAddress(),
                request.getDetailAddress(),
                request.getPostcode(),
                request.getPhone(),
                request.getPoint());
        return userNo;
    }
}
