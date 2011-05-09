package controllers;

import play.mvc.With;
import controllers.security.*;

@Check("admin")
@With(Secure.class)
public class Users extends CRUD {
	// no implementation needed
}