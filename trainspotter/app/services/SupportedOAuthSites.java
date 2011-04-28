package services;

import org.scribe.builder.api.*;

public enum SupportedOAuthSites {
	TWITTER(TwitterApi.class, "BgemIlLgxX0vCTPMAN61g", "PARAM1");

	private final Class<? extends Api> apiClass;
	private final String applicationApiKey;
	private String applicationSecretKey;

	private SupportedOAuthSites(Class<? extends Api> apiClass, String applicationApiKey, String applicationSecretKeyParameterName) {
		this.apiClass = apiClass;
		this.applicationApiKey = applicationApiKey;
		applicationSecretKey = new SystemManager().getProperty(applicationSecretKeyParameterName);
	}

	public Class<? extends Api> getApiClass() {
		return apiClass;
	}

	public String getApplicationApiKey() {
		return applicationApiKey;
	}

	public String getApplicationSecretKey() {
		return applicationSecretKey;
	}
}
