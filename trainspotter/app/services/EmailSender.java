package services;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.ListVerifiedEmailAddressesResult;
import com.google.common.base.Throwables;

public class EmailSender {
	private final SystemManager systemManager;
	private final AmazonSimpleEmailServiceProvider amazonSimpleEmailServiceProvider;

	public EmailSender(SystemManager systemManager, AmazonSimpleEmailServiceProvider amazonSimpleEmailServiceProvider) {
		this.systemManager = systemManager;
		this.amazonSimpleEmailServiceProvider = amazonSimpleEmailServiceProvider;
	}

	public void sendEmail(String to, String title, String body, String from) {
		String accessKey = systemManager.getProperty("AWS_ACCESS_KEY_ID");
		String secretKey = systemManager.getProperty("AWS_SECRET_KEY");
		AmazonSimpleEmailService email = amazonSimpleEmailServiceProvider.getService(accessKey, secretKey);
		ListVerifiedEmailAddressesResult verifiedEmails = email.listVerifiedEmailAddresses();
		for (String address : new String[] { to, from }) {
			if (!verifiedEmails.getVerifiedEmailAddresses().contains(address)) {
				email.verifyEmailAddress(amazonSimpleEmailServiceProvider.getVerifyEmailAddressRequest(address));
				// verification email sent
				return;
			}
		}

		Session mailSession = createMailSession(accessKey, secretKey);

		try {
			// Create a new Message
			Message msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject(title);
			msg.setText(body);
			msg.saveChanges();

			// Reuse one Transport object for sending all your messages
			// for better performance
			Transport t = amazonSimpleEmailServiceProvider.getTransport(mailSession);
			t.connect();
			t.sendMessage(msg, null);

			// Close your transport when you're completely done sending
			// all your messages
			t.close();
		} catch (AddressException e) {
			Throwables.propagate(e);
		} catch (MessagingException e) {
			Throwables.propagate(e);
		}
	}

	private static Session createMailSession(String accessKey, String secretKey) {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "aws");
		props.setProperty("mail.aws.user", accessKey);
		props.setProperty("mail.aws.password", secretKey);

		Session mailSession = Session.getInstance(props);
		return mailSession;
	}

}
