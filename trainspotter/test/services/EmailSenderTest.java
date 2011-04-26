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
import controllers.TrainSpotter;

public class EmailSenderTest extends UnitTest {
	private final SystemManager mockSystemManager = mock(SystemManager.class);
	private final AmazonSimpleEmailServiceProvider mockAmazonSimpleEmailServiceProvider = mock(AmazonSimpleEmailServiceProvider.class);
	private final AmazonSimpleEmailService mockAmazonSimpleEmailService = mock(AmazonSimpleEmailService.class);
	private final ListVerifiedEmailAddressesResult mockListVerifiedEmailAddressesResult = mock(ListVerifiedEmailAddressesResult.class);
	private final VerifyEmailAddressRequest mockVerifyEmailAddressRequest = mock(VerifyEmailAddressRequest.class);
	private final Transport mockTransport = mock(Transport.class);

	@Test
	public void shouldVerifyEmailAddresses() {
		when(mockSystemManager.getProperty("AWS_ACCESS_KEY_ID")).thenReturn("accessKey");
		when(mockSystemManager.getProperty("AWS_SECRET_KEY")).thenReturn("secretKey");
		when(mockAmazonSimpleEmailServiceProvider.getVerifyEmailAddressRequest("ericlef@gmail.com")).thenReturn(mockVerifyEmailAddressRequest);
		when(mockAmazonSimpleEmailServiceProvider.getService("accessKey", "secretKey")).thenReturn(mockAmazonSimpleEmailService);
		when(mockAmazonSimpleEmailService.listVerifiedEmailAddresses()).thenReturn(mockListVerifiedEmailAddressesResult);
		when(mockListVerifiedEmailAddressesResult.getVerifiedEmailAddresses()).thenReturn(Lists.<String> newArrayList());

		new EmailSender(mockSystemManager, mockAmazonSimpleEmailServiceProvider).sendEmail();

		verify(mockAmazonSimpleEmailService).verifyEmailAddress(mockVerifyEmailAddressRequest);
	}

	@Test
	public void shouldSendEmails() throws MessagingException {
		when(mockSystemManager.getProperty("AWS_ACCESS_KEY_ID")).thenReturn("accessKey");
		when(mockSystemManager.getProperty("AWS_SECRET_KEY")).thenReturn("secretKey");
		when(mockAmazonSimpleEmailServiceProvider.getService("accessKey", "secretKey")).thenReturn(mockAmazonSimpleEmailService);
		when(mockAmazonSimpleEmailServiceProvider.getTransport(sessionWithProperties("aws", "accessKey", "secretKey"))).thenReturn(mockTransport);
		when(mockAmazonSimpleEmailService.listVerifiedEmailAddresses()).thenReturn(mockListVerifiedEmailAddressesResult);
		when(mockListVerifiedEmailAddressesResult.getVerifiedEmailAddresses()).thenReturn(Lists.<String> newArrayList("ericlef@gmail.com"));

		new EmailSender(mockSystemManager, mockAmazonSimpleEmailServiceProvider).sendEmail();

		verify(mockAmazonSimpleEmailService, never()).verifyEmailAddress(mockVerifyEmailAddressRequest);
		InOrder inOrder = inOrder(mockTransport);
		inOrder.verify(mockTransport).connect();
		Message msg = new MimeMessage(session("aws", "accessKey", "secretKey"));
		msg.setFrom(new InternetAddress("ericlef@gmail.com"));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress("ericlef@gmail.com"));
		msg.setSubject(TrainSpotter.SUBJECT);
		msg.setText(TrainSpotter.BODY);
		msg.saveChanges();
		verify(mockTransport).sendMessage((Message) argThat(new ReflectionEquals(msg, "dh", "contentStream", "headers", "session")), (Address[]) isNull());
		inOrder.verify(mockTransport).sendMessage(msg, null);
		inOrder.verify(mockTransport).close();
	}

	private static Session sessionWithProperties(String transportProtocol, String accessKey, String secretKey) {
		Session session = session(transportProtocol, accessKey, secretKey);
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
