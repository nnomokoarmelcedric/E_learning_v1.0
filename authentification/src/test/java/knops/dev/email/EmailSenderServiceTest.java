package knops.dev.email;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EmailSenderServiceTest {

    @InjectMocks
    private EmailSenderService emailSenderService;

    @Mock
    private JavaMailSender mailSender;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendSimpleEmail_Success() {
        EmailRequest emailRequest = EmailRequest.builder()
                .from("sender@example.com")
                .to("recipient@example.com")
                .subject("Test Email")
                .text("This is a test email.")
                .build();


        ResponseEntity<String> response = emailSenderService.sendSimpleEmail(emailRequest);

        verify(mailSender).send(any(SimpleMailMessage.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Mail Sent Successfully", response.getBody());
    }

    @Test
    public void testSendSimpleEmail_Failure() {
        EmailRequest emailRequest = EmailRequest.builder()
                .from("sender@example.com")
                .to("recipient@example.com")
                .subject("Test Email")
                .text("This is a test email.")
                .build();

        ResponseEntity<String> response = emailSenderService.sendSimpleEmail(emailRequest);

        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}
