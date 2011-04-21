package models;

import java.util.regex.*;
import org.apache.commons.lang.builder.*;

public class ResultsPageParser {
	private final String content;

	public ResultsPageParser(String content) {
		this.content = content;
	}

	public String getTrainNumber() {
		return extract("Train \\D*([0-9]*)<");
	}

	public String getDepartureDate() {
		return extract("\\(le (.*)\\)<");
	}

	private String extract(String regex) {
		Pattern pattern = Pattern.compile(regex);

		String[] splittedPage = content.split("\n");

		String value = null;
		for (String line : splittedPage) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				value = matcher.group(1);
			}
		}
		return value;
	}

	public String getContent() {
		return content;
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
