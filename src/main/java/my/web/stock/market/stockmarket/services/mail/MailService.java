package my.web.stock.market.stockmarket.services.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MailService {
    @Value("${spring.mail.username}")
    private String username;

    private final ExecutorService executorService;
    private final int THREAD_POOL_SIZE = 10;
    private final JavaMailSender mailSender;
    private final String VERIFY_SUBJECT = "Verify Email for Stock Market";

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public void sendVerificationCode(String mail, String code) {
        send(mail, VERIFY_SUBJECT, code);
    }

    public void send(String mailTo, String subjectLine, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(mailTo);
        mailMessage.setFrom(username);
        mailMessage.setSubject(subjectLine);
        mailMessage.setText(text);

        executorService.submit(() -> mailSender.send(mailMessage));
    }

}

