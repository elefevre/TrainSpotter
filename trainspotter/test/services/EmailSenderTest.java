package services;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.*;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import play.test.UnitTest;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.google.common.collect.Lists;

public class EmailSenderTest extends UnitTest {
	private final SystemManager mockSystemManager = mock(SystemManager.class);
	private final AmazonSimpleEmailServiceProvider mockAmazonSimpleEmailServiceProvider = mock(AmazonSimpleEmailServiceProvider.class);
	private final AmazonSimpleEmailService mockAmazonSimpleEmailService = mock(AmazonSimpleEmailService.class);
	private final ListVerifiedEmailAddressesResult mockListVerifiedEmailAddressesResult = mock(ListVerifiedEmailAddressesResult.class);
	private final VerifyEmailAddressRequest mockVerifyEmailAddressRequestForSender = mock(VerifyEmailAddressRequest.class);
	private final VerifyEmailAddressRequest mockVerifyEmailAddressRequestForReceipient = mock(VerifyEmailAddressRequest.class);
	private final Transport mockTransport = mock(Transport.class);

	@Test
	public void shouldVerifyTheEmailAddressOfTheSender() {
		when(mockSystemManager.getProperty("AWS_ACCESS_KEY_ID")).thenReturn("accessKey");
		when(mockSystemManager.getProperty("AWS_SECRET_KEY")).thenReturn("secretKey");
		when(mockAmazonSimpleEmailServiceProvider.getVerifyEmailAddressRequest("ericlef@gmail.com")).thenReturn(mockVerifyEmailAddressRequestForSender);
		when(mockAmazonSimpleEmailServiceProvider.getService("accessKey", "secretKey")).thenReturn(mockAmazonSimpleEmailService);
		when(mockAmazonSimpleEmailService.listVerifiedEmailAddresses()).thenReturn(mockListVerifiedEmailAddressesResult);
		when(mockListVerifiedEmailAddressesResult.getVerifiedEmailAddresses()).thenReturn(Lists.<String> newArrayList("user@site.com"));

		new EmailSender(mockSystemManager, mockAmazonSimpleEmailServiceProvider).sendEmail("user@site.com", null, null, "ericlef@gmail.com");

		verify(mockAmazonSimpleEmailService).verifyEmailAddress(mockVerifyEmailAddressRequestForSender);
	}

	@Test
	public void shouldVerifyTheEmailAddressOfTheReceipient() {
		when(mockSystemManager.getProperty("AWS_ACCESS_KEY_ID")).thenReturn("accessKey");
		when(mockSystemManager.getProperty("AWS_SECRET_KEY")).thenReturn("secretKey");
		when(mockAmazonSimpleEmailServiceProvider.getVerifyEmailAddressRequest("user@site.com")).thenReturn(mockVerifyEmailAddressRequestForReceipient);
		when(mockAmazonSimpleEmailServiceProvider.getService("accessKey", "secretKey")).thenReturn(mockAmazonSimpleEmailService);
		when(mockAmazonSimpleEmailService.listVerifiedEmailAddresses()).thenReturn(mockListVerifiedEmailAddressesResult);
		when(mockListVerifiedEmailAddressesResult.getVerifiedEmailAddresses()).thenReturn(Lists.<String> newArrayList("ericlef@gmail.com"));

		new EmailSender(mockSystemManager, mockAmazonSimpleEmailServiceProvider).sendEmail("user@site.com", null, null, "ericlef@gmail.com");

		verify(mockAmazonSimpleEmailService).verifyEmailAddress(mockVerifyEmailAddressRequestForReceipient);
	}

	@Test
	public void shouldSendEmails() throws MessagingException {
		Session session = session("aws", "accessKey", "secretKey");

		when(mockSystemManager.getProperty("AWS_ACCESS_KEY_ID")).thenReturn("accessKey");
		when(mockSystemManager.getProperty("AWS_SECRET_KEY")).thenReturn("secretKey");
		when(mockAmazonSimpleEmailServiceProvider.getService("accessKey", "secretKey")).thenReturn(mockAmazonSimpleEmailService);
		when(mockAmazonSimpleEmailServiceProvider.getTransport(sessionEqualTo(session))).thenReturn(mockTransport);
		when(mockAmazonSimpleEmailService.listVerifiedEmailAddresses()).thenReturn(mockListVerifiedEmailAddressesResult);
		when(mockListVerifiedEmailAddressesResult.getVerifiedEmailAddresses()).thenReturn(Lists.<String> newArrayList("ericlef@gmail.com", "user@site.com"));

		new EmailSender(mockSystemManager, mockAmazonSimpleEmailServiceProvider).sendEmail("user@site.com", "subject", "email body", "ericlef@gmail.com");

		verify(mockAmazonSimpleEmailService, never()).verifyEmailAddress(any(VerifyEmailAddressRequest.class));
		InOrder inOrder = inOrder(mockTransport);
		inOrder.verify(mockTransport).connect();
		inOrder.verify(mockTransport).sendMessage(mimeMessage("ericlef@gmail.com", "user@site.com", "subject", "email body"), (Address[]) isNull());
		inOrder.verify(mockTransport).close();
	}

	private static Message mimeMessage(String from, String to, String title, String body) throws AddressException, MessagingException {
		Message msg = new MimeMessage(Session.getInstance(new Properties()));
		msg.setFrom(new InternetAddress(from));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		msg.setSubject(title);
		msg.setText(body);
		msg.saveChanges();
		return (Message) argThat(new ReflectionEquals(msg, "dh", "contentStream", "headers", "session")); // would be nice to test session too
	}

	private static Session sessionEqualTo(Session session) {
		return (Session) argThat(new ReflectionEquals(session, "providers", "providersByProtocol", "providersByClassName"));
	}

	private static Session session(String transportProtocol, String accessKey, String secretKey) {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", transportProtocol);
		props.setProperty("mail.aws.user", accessKey);
		props.setProperty("mail.aws.password", secretKey);

		return Session.getInstance(props);
	}

}
