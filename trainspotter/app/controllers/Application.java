package controllers;

import java.util.List;
import models.User;
import play.mvc.Controller;

public class Application extends Controller {

	public static void index() {
		@SuppressWarnings("static-access") List<User> users = User.findAll();
		render(users);
	}

}