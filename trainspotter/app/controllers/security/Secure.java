package controllers.security;

import static org.scribe.model.Verb.*;
import static services.SupportedOAuthSites.*;
import models.User;
import org.scribe.model.*;
import play.data.validation.Required;
import play.libs.Crypto;
import play.mvc.*;
import services.RegexUtils;

public class Secure extends Controller {
	private static final String USER_ID_PROPERTY_NAME = "userId";

	@Before(unless = { "login", "authenticate", "logout" })
	static void checkAccess() {
		if (!isConnected()) {
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
		for (@SuppressWarnings("unused") String profile : check.value()) {
			boolean hasProfile = true; // to be implemented
			if (!hasProfile) {
				// to be implemented
			}
		}
	}

	public static void login() {
		Http.Cookie remember = request.cookies.get("rememberme");
		if ((remember != null) && (remember.value.indexOf("-") > 0)) {
			String sign = remember.value.substring(0, remember.value.indexOf("-"));
			String userId = remember.value.substring(remember.value.indexOf("-") + 1);
			if (Crypto.sign(userId).equals(sign)) {
				markUserAsConnected(Long.parseLong(userId));
				if (isConnected()) {
					redirectToOriginalURL();
				}
			}
		}

		flash.keep("url");

		Token requestToken = TWITTER.createService().getRequestToken();
		User user = new User(TWITTER, null, null, requestToken);
		user.save();

		String requestUrl = TWITTER.createService().getAuthorizationUrl(requestToken);
		Long userId = user.id;

		render(requestUrl, userId);
	}

	public static void authenticate(@Required String verifierCode, @Required Long userId) {
		@SuppressWarnings("static-access") User user = User.findById(userId);
		Token requestToken = user.getToken();
		Token accessToken = TWITTER.createService().getAccessToken(requestToken, new Verifier(verifierCode));

		OAuthRequest oauthRequest = new OAuthRequest(GET, "http://api.twitter.com/1/account/verify_credentials.xml");
		TWITTER.createService().signRequest(accessToken, oauthRequest);
		Response oauthResponse = oauthRequest.send();

		String id = RegexUtils.extractFirstMatchInMultiLines("<id>(.*)</id>", oauthResponse.getBody());
		String name = RegexUtils.extractFirstMatchInMultiLines("<name>(.*)</name>", oauthResponse.getBody());
		@SuppressWarnings("static-access") User existingUser = User.find("byIdOnAuthSite", id).first();

		if (existingUser != null) {
			user.delete();
			saveUser(existingUser, accessToken, id, name);
		} else {
			saveUser(user, accessToken, id, name);
		}

		markUserAsConnected(user.id);
		response.setCookie("rememberme", Crypto.sign("" + userId) + "-" + userId, "30d");
		// Redirect to the original URL (or /)
		redirectToOriginalURL();
	}

	private static void saveUser(User user, Token accessToken, String id, String name) {
		user.idOnAuthSite = id;
		user.name = name;
		user.setToken(accessToken);
		user.save();
	}

	public static void logout() {
		session.clear();
		response.removeCookie("rememberme");
		flash.success("secure.logout");
		redirect("/");
	}

	static void redirectToOriginalURL() {
		String url = flash.get("url");
		if (url == null) {
			url = "/";
		}
		redirect(url);
	}

	static String getConnectedUserId() {
		return session.get(USER_ID_PROPERTY_NAME);
	}

	private static void markUserAsConnected(Long userId) {
		session.put(USER_ID_PROPERTY_NAME, userId);
	}

	@SuppressWarnings("static-access")
	public static User connected() {
		String userId = getConnectedUserId();
		long userIdAsLong;
		try {
			userIdAsLong = Long.parseLong(userId);
		} catch (Throwable e) {
			return null;
		}
		return User.findById(userIdAsLong);
	}

	public static boolean isConnected() {
		return connected() != null;
	}

}
