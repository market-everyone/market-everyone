package web.seller.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import web.common.util.ImageUtil;
import web.seller.controller.dto.SellerRequestDTO;
import web.seller.controller.dto.SellerResponseDTO;
import web.seller.domain.Seller;
import web.seller.domain.SellerRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void save(SellerRequestDTO dto, HttpServletResponse res, MultipartFile file) throws IOException {
        ImageUtil imageUtil = new ImageUtil();

        String imagePath = imageUtil.uploadImage(res, file, 0L);
        dto.setImagePath(imagePath);
        Seller seller = dto.encodePassword(passwordEncoder).toEntity();
        sellerRepository.save(seller);
    }

    @Transactional(readOnly = true)
    public boolean checkSellerSignUpRequestValidation(SellerRequestDTO sellerRequestDTO, Model model) {
        Map<String, String> errors = new HashMap<>();
        if (sellerRepository.existsByEmail(sellerRequestDTO.getEmail())) {
            errors.put("emailError", "true");
        }

        if (sellerRepository.existsByBrandName(sellerRequestDTO.getBrandName())) {
            errors.put("brandNameError", "true");
        }

        if (!sellerRequestDTO.checkPasswordConfirm()) {
            errors.put("passwordError", "true");
        }

        model.addAttribute("errors", errors);
        return errors.isEmpty();
    }

    public Seller login(String email, String password) {
        return sellerRepository.findByEmail(email)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    public Seller findById(Long id) {

        SellerResponseDTO sellerResponseDTO = new SellerResponseDTO();
        return sellerRepository.findById(id).orElse(sellerResponseDTO.toSeller());
    }
}
