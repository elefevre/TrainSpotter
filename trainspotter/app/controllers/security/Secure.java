package controllers.security;

import static org.scribe.model.Verb.*;
import static services.SupportedOAuthSites.*;
import models.*;
import models.User.Status;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import play.data.validation.*;
import play.libs.Crypto;
import play.mvc.*;
import services.RegexUtils;

public class Secure extends Controller {
	@Before(unless = { "login", "authenticate", "logout" })
	static void checkAccess() {
		if (!Security.isConnected()) {
			flash.put("url", "GET".equals(request.method) ? request.url : "/"); // seems a good default
			login();
		}
		Check check = getActionAnnotation(Check.class);
		if (check != null) {
			check(check);
		}
		check = getControllerInheritedAnnotation(Check.class);
		if (check != null) {
			check(check);
		}
	}

	private static void check(Check check) {
		for (String profile : check.value()) {
			boolean hasProfile = Security.check(profile);
			if (!hasProfile) {
				Security.onCheckFailed(profile);
			}
		}
	}

	public static void login() {
		Http.Cookie remember = request.cookies.get("rememberme");
		if ((remember != null) && (remember.value.indexOf("-") > 0)) {
			String sign = remember.value.substring(0, remember.value.indexOf("-"));
			String userId = remember.value.substring(remember.value.indexOf("-") + 1);
			if (Crypto.sign(userId).equals(sign)) {
				session.put("userId", userId);
				redirectToOriginalURL();
			}
		}
		flash.keep("url");

		OAuthService service = TWITTER.createService();
		Token requestToken = service.getRequestToken();
		User user = new User(TWITTER, null, null, requestToken, Status.ACCESS_REQUESTED);
		user.save();

		String requestUrl = service.getAuthorizationUrl(requestToken);
		Long userId = user.id;

		render(requestUrl, userId);
	}

	public static void authenticate(@Required String verifierCode, @Required Long userId) {
		Boolean allowed = false;
		@SuppressWarnings("static-access") User user = User.findById(userId);
		Token requestToken = user.getToken();
		OAuthService service = TWITTER.createService();
		Token accessToken = service.getAccessToken(requestToken, new Verifier(verifierCode));

		OAuthRequest oauthRequest = new OAuthRequest(GET, "http://api.twitter.com/1/account/verify_credentials.xml");
		service.signRequest(accessToken, oauthRequest);
		Response oauthResponse = oauthRequest.send();
		System.out.println(oauthResponse.getBody());

		String id = RegexUtils.extractFirstMatchInMultiLines("<id>(.*)</id>", oauthResponse.getBody());
		String name = RegexUtils.extractFirstMatchInMultiLines("<name>(.*)</name>", oauthResponse.getBody());
		user.idOnAuthSite = id;
		user.name = name;
		user.setToken(accessToken);
		user.status = Status.AUTHENTICATED;
		user.save();

		allowed = true;

		if (Validation.hasErrors() || !allowed) {
			flash.keep("url");
			flash.error("secure.error");
			params.flash();
			login();
		}
		// Mark user as connected
		session.put("userId", user.id);
		response.setCookie("rememberme", Crypto.sign("" + userId) + "-" + userId, "30d");
		// Redirect to the original URL (or /)
		redirectToOriginalURL();
	}

	public static void logout() {
		session.clear();
		response.removeCookie("rememberme");
		flash.success("secure.logout");
		login();
	}

	static void redirectToOriginalURL() {
		String url = flash.get("url");
		if (url == null) {
			url = "/";
		}
		redirect(url);
	}

	public static class Security extends Controller {

		/**
		 * This method checks that a profile is allowed to view this
		 * page/method. This method is called prior to the method's controller
		 * annotated with the @Check method.
		 * 
		 * @param profile
		 * @return true if you are allowed to execute this controller method.
		 */
		static boolean check(String profile) {
			return true;
		}

		@SuppressWarnings("static-access")
		static User connected() {
			String userId = session.get("userId");
			if (userId == null) {
				return null;
			}
			try {
				Long.parseLong(userId);
			} catch (Throwable e) {
				return null;
			}
			return User.findById(Long.parseLong(userId));
		}

		/**
		 * Indicate if a user is currently connected
		 * 
		 * @return true if the user is connected
		 */
		static boolean isConnected() {
			return connected() != null;
		}

		/**
		 * This method is called if a check does not succeed. By default it
		 * shows the not allowed page (the controller forbidden method).
		 * 
		 * @param profile
		 */
		static void onCheckFailed(String profile) {
			forbidden();
		}

	}

}
