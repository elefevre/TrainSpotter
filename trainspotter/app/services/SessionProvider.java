package services;

import java.util.Properties;
import javax.mail.Session;

public class SessionProvider {

	public Session getInstance(Properties properties) {
		return Session.getInstance(properties);
	}

}
