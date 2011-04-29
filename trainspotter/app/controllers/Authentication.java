package controllers;

import static org.scribe.model.Verb.*;
import java.util.Scanner;
import models.User;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import play.mvc.Controller;
import services.*;

public class Authentication extends Controller {
	public static void login() {
		OAuthService service = new ServiceBuilder()//
				.provider(SupportedOAuthSites.TWITTER.getApiClass())//
				.apiKey(SupportedOAuthSites.TWITTER.getApplicationApiKey())//
				.apiSecret(SupportedOAuthSites.TWITTER.getApplicationSecretKey())//
				.build();
		Token requestToken = service.getRequestToken();

		Scanner in = new Scanner(System.in);
		System.out.println("autorization url= " + service.getAuthorizationUrl(requestToken));
		System.out.println("And paste the verifier here");
		System.out.print(">>");
		String verifierCode = in.nextLine();

		Token accessToken = service.getAccessToken(requestToken, new Verifier(verifierCode));

		OAuthRequest oauthRequest = new OAuthRequest(GET, "http://api.twitter.com/1/account/verify_credentials.xml");
		service.signRequest(accessToken, oauthRequest);
		Response oauthResponse = oauthRequest.send();
		System.out.println(oauthResponse.getBody());

		String id = RegexUtils.extractFirstMatchInMultiLines("<id>(.*)</id>", oauthResponse.getBody());
		String name = RegexUtils.extractFirstMatchInMultiLines("<name>(.*)</name>", oauthResponse.getBody());
		new User(SupportedOAuthSites.TWITTER, id, name).save();
	}
}