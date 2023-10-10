package knops.dev.email;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmailRequestTest {

    @Test
    public void testEmailRequestProperties() {
        EmailRequest emailRequest = EmailRequest.builder()
                .from("sender@example.com")
                .to("recipient@example.com")
                .subject("Test Email")
                .text("This is a test email.")
                .build();

        assertEquals("sender@example.com", emailRequest.getFrom());
        assertEquals("recipient@example.com", emailRequest.getTo());
        assertEquals("Test Email", emailRequest.getSubject());
        assertEquals("This is a test email.", emailRequest.getText());
    }
}
