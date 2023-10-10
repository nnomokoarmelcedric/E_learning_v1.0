package knops.dev.email;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/knops/auth")
public class EmailController {

    private final EmailSenderService emailSenderService;



    @PostMapping("/send-email")
    public void sendEmail(@RequestBody EmailRequest emailRequest) {
        emailSenderService.sendSimpleEmail(emailRequest);
    }
}
