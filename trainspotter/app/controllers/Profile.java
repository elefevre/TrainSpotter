package controllers;

import static controllers.helper.Redirects.*;
import models.User;
import org.apache.commons.lang.StringUtils;
import play.mvc.Controller;
import controllers.security.Secure;

public class Profile extends Controller {
	public static void edit(String emailAddress) {
		if (!Secure.isConnected()) {
			redirectToHomePage();
		}

		if (StringUtils.isNotBlank(emailAddress)) {
			User user = Secure.connected();
			user.email = emailAddress;
			user.save();

			flash.success("Votre adresse email est maintenant %s.", user.email);
		}

		render();
	}
}