package models;

import javax.persistence.Entity;
import org.apache.commons.lang.builder.*;
import play.db.jpa.Model;
import services.SupportedOAuthSites;

@Entity
public class User extends Model {
	public String idOnSite;
	public SupportedOAuthSites oAuthSite;
	public String name;

	public User(SupportedOAuthSites oAuthSite, String idOnSite, String name) {
		this.oAuthSite = oAuthSite;
		this.idOnSite = idOnSite;
		this.name = name;
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
