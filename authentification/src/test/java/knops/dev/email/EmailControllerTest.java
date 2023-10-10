package knops.dev.email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

public class EmailControllerTest {

    private EmailController emailController;

    @Mock
    private EmailSenderService emailSenderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        emailController = new EmailController(emailSenderService);
    }

    @Test
    public void testSendEmail_Success() {
        EmailRequest emailRequest = new EmailRequest("sender@example.com", "recipient@example.com", "Test Email", "This is a test email");

        when(emailSenderService.sendSimpleEmail(emailRequest)).thenReturn(ResponseEntity.ok("Mail Sent Successfully"));

        emailController.sendEmail(emailRequest);

        verify(emailSenderService).sendSimpleEmail(emailRequest);
    }

    @Test
    public void testSendEmail_Failure() {
        EmailRequest emailRequest = new EmailRequest("sender@example.com", "recipient@example.com", "Test Email", "This is a test email");

        when(emailSenderService.sendSimpleEmail(emailRequest)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email"));

        emailController.sendEmail(emailRequest);

        verify(emailSenderService).sendSimpleEmail(emailRequest);
    }
}
