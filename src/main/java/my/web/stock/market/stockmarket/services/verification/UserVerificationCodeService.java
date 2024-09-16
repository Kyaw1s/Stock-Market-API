package my.web.stock.market.stockmarket.services.verification;

import lombok.RequiredArgsConstructor;
import my.web.stock.market.stockmarket.dto.user.main.UserCredentialDto;
import my.web.stock.market.stockmarket.repositories.entities.jpa.User;
import my.web.stock.market.stockmarket.repositories.entities.jpa.UserVerificationCode;
import my.web.stock.market.stockmarket.dto.user.verification.UserVerificationDTO;
import my.web.stock.market.stockmarket.repositories.jpa.UserRepository;
import my.web.stock.market.stockmarket.repositories.jpa.UserVerificationCodeRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserVerificationCodeService {
    public final int TIME_OUT_FOR_VERIFICATION_IN_MINUTES = 1;

    private final UserVerificationCodeRepository userVerificationCodeRepository;
    private final UserRepository userRepository;

    private final int MIN_VALUE_OF_VERIFICATION_CODE = 100000;
    private final int MAX_VALUE_OF_VERIFICATION_CODE = 999999;

    @Transactional
    public void saveVerifyCodeForUser(UserCredentialDto userCredentialDto, String code) {
        User user = userRepository.findUserByEmail(userCredentialDto.getEmail());

        UserVerificationCode userVerificationCode = userVerificationCodeRepository.findByUser(user);

        if(userVerificationCode != null) {
            userVerificationCodeRepository.delete(userVerificationCode);
        }

        userVerificationCodeRepository.save(new UserVerificationCode(user, code));
    }

    public String generateVerifyCodeForUser() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(MIN_VALUE_OF_VERIFICATION_CODE, MAX_VALUE_OF_VERIFICATION_CODE + 1));
    }

    public boolean checkVerificationCode(UserVerificationDTO userVerificationDTO) {
        User user = userRepository.findUserByEmail(userVerificationDTO.getEmail());
        UserVerificationCode userVerificationCode = userVerificationCodeRepository.findByUser(user);

        if(userVerificationCode == null) {
            return false;
        }

        return isVerificationCodeActive(userVerificationCode.getSentDate()) && userVerificationCode.getCode().equals(userVerificationDTO.getVerificationCode());
    }

    private boolean isVerificationCodeActive(Date sendDate) {
        Date now = new Date(System.currentTimeMillis());
        long durationInMinutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - sendDate.getTime());

        return durationInMinutes < TIME_OUT_FOR_VERIFICATION_IN_MINUTES;
    }

    @Scheduled(fixedDelay = 50000)
    @Async
    @Transactional
    public void cleanInactiveVerificationCodes() {
        List<UserVerificationCode> userVerificationCodes = userVerificationCodeRepository.findAll();

        for(var userVerificationCode : userVerificationCodes) {
            if(!isVerificationCodeActive(userVerificationCode.getSentDate())) {
                userVerificationCodeRepository.delete(userVerificationCode);
            }
        }
    }

}
