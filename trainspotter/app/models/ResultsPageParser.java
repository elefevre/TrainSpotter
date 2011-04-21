package models;

import java.util.regex.*;
import org.apache.commons.lang.builder.*;

public class ResultsPageParser {
	private final String content;

	public ResultsPageParser(String content) {
		this.content = content;
	}

	public String getTrainNumber() {
		Pattern pattern = Pattern.compile("Train \\D*([0-9]*)<");

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

	public String getDepartureDate() {
		Pattern pattern = Pattern.compile("\\(le (.*)\\)<");

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
