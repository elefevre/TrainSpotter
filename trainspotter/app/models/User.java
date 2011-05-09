package models;

import javax.persistence.Entity;
import org.apache.commons.lang.builder.*;
import org.scribe.model.Token;
import play.db.jpa.Model;
import services.SupportedOAuthSites;

@Entity
public class User extends Model {
	public String idOnAuthSite;
	public SupportedOAuthSites oAuthSite;
	public String name;
	String tokenValue;
	String tokenSecret;

	public User(SupportedOAuthSites oAuthSite, String idOnAuthSite, String name, Token token) {
		this.oAuthSite = oAuthSite;
		this.idOnAuthSite = idOnAuthSite;
		this.name = name;
		setToken(token);
	}

	public Token getToken() {
		return new Token(tokenValue, tokenSecret);
	}

	public void setToken(Token token) {
		tokenValue = token.getToken();
		tokenSecret = token.getSecret();
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object arg0) {
		return EqualsBuilder.reflectionEquals(this, arg0);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
