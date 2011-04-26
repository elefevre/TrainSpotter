package services;

import javax.mail.*;
import com.amazonaws.auth.*;
import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.VerifyEmailAddressRequest;

public class AmazonSimpleEmailServiceProvider {

	public AWSCredentials getCredentials(String accessKey, String secretKey) {
		return new BasicAWSCredentials(accessKey, secretKey);
	}

	public AmazonSimpleEmailService getService(String accessKey, String secretKey) {
		return new AmazonSimpleEmailServiceClient(new BasicAWSCredentials(accessKey, secretKey));
	}

	public VerifyEmailAddressRequest getVerifyEmailAddressRequest(String email) {
		return new VerifyEmailAddressRequest().withEmailAddress(email);
	}

	public Transport getTransport(Session instance) {
		return new AWSJavaMailTransport(instance, null);
	}

}
