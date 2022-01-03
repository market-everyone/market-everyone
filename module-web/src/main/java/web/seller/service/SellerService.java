package web.seller.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import web.seller.controller.dto.SellerLoginDTO;
import web.seller.controller.dto.SellerRequestDTO;
import web.seller.domain.Seller;
import web.seller.domain.SellerRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class SellerService {

    private final SellerRepository sellerRepository;

    @Transactional
    public void save(SellerRequestDTO dto) {
        sellerRepository.save(dto.toEntity());
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

    public Optional<Seller> findById(Long id) {
        return sellerRepository.findById(id);
    }
}
