package controllers;

import static play.data.validation.Validation.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.*;
import models.*;
import org.joda.time.DateTime;
import play.data.validation.Required;
import play.mvc.Controller;
import services.ResultsPageParser;
import services.StatusPageRetriever;
import services.URLConnectionProvider;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;


public class TrainSpotter extends Controller {
	private static final String TO = "ericlef@gmail.com";
	private static final String FROM = "ericlef@gmail.com";
	private static final String BODY = "Hello World!";
	private static final String SUBJECT = "Hello World!";

	public static void displayTrainDetails(@Required String trainNumber) throws MalformedURLException, IOException {
		if (hasErrors()) {
			flash.error("Please enter the train number");
			Application.index();
		}

		DateTime today = new DateTime();

		TrainInformationPage results = getStatusPageRetriever().downloadStatusPageForTrain(trainNumber, today.getYear(), today.getMonthOfYear(), today.getDayOfMonth());

		render(results);
	}

	public static void displaySystemProperties() {
		Set<Entry<Object,Object>> properties = System.getProperties().entrySet();

		render(properties);
	}

	public static void sendEmail(String title) {
		/*
		 * Important: Be sure to fill in your AWS access credentials in the
		 * AwsCredentials.properties file before you try to run this sample.
		 * http://aws.amazon.com/security-credentials
		 */
		String accessKey = "";
		String secretKey = "";
		BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

		/*
		 * SES requires that the sender and receiver of each message be verified
		 * through the service. The verifyEmailAddress interface will send the
		 * given address a verification message with a URL they can click to
		 * verify that address.
		 */
		AmazonSimpleEmailService email = new AmazonSimpleEmailServiceClient(credentials);
		ListVerifiedEmailAddressesResult verifiedEmails = email.listVerifiedEmailAddresses();
		for (String address : new String[] { TO, FROM }) {
			if (!verifiedEmails.getVerifiedEmailAddresses().contains(address)) {
				email.verifyEmailAddress(new VerifyEmailAddressRequest().withEmailAddress(address));
				System.out.println("Please check the email address " + address + " to verify it");
				System.exit(0);
			}
		}

		/*
		 * Get JavaMail Properties and Setup Session
		 */
		Properties props = new Properties();

		/*
		 * Setup JavaMail to use the Amazon Simple Email Service by specifying
		 * the "aws" protocol.
		 */
		props.setProperty("mail.transport.protocol", "aws");

		/*
		 * Setting mail.aws.user and mail.aws.password are optional. Setting
		 * these will allow you to send mail using the static transport send()
		 * convince method. It will also allow you to call connect() with no
		 * parameters. Otherwise, a user name and password must be specified in
		 * connect.
		 */
		props.setProperty("mail.aws.user", credentials.getAWSAccessKeyId());
		props.setProperty("mail.aws.password", credentials.getAWSSecretKey());

		Session session = Session.getInstance(props);

		try {
			// Create a new Message
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(FROM));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(TO));
			msg.setSubject(SUBJECT);
			msg.setText(BODY);
			msg.saveChanges();

			// Reuse one Transport object for sending all your messages
			// for better performance
			Transport t = new AWSJavaMailTransport(session, null);
			t.connect();
			t.sendMessage(msg, null);

			// Close your transport when you're completely done sending
			// all your messages
			t.close();

		} catch (AddressException e) {
			e.printStackTrace();
			System.out.println("Caught an AddressException, which means one or more of your " + "addresses are improperly formatted.");
		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("Caught a MessagingException, which means that there was a " + "problem sending your message to Amazon's E-mail Service check the " + "stack trace for more information.");
		}
	}

	private static StatusPageRetriever getStatusPageRetriever() {
		return new StatusPageRetriever(new URLConnectionProvider(), new ResultsPageParser());
	}
}