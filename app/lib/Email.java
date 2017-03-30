import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import javax.inject.Inject;
import java.io.File;
import org.apache.commons.mail.EmailAttachment;

public class Email {
    @Inject MailerClient mailerClient;

//    Email() {}

    public void sendResetPasswordEmail(String newPassword, String email) {
        String cid = "1234";
//        Email email = new Email()
//                .setSubject("Your new password for Conference Management System")
//                .setFrom("Mister FROM <from@email.com>")
//                .addTo("Miss TO <" + email + ">")
//                .setBodyText("Hi! Your new password is: " + newPassword);
//        mailerClient.send(email);
    }
}