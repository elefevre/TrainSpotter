package controllers.helper;

import play.mvc.Controller;

public class Redirects extends Controller {

	public static void redirectToHomePage() {
		redirect("/");
	}

}
