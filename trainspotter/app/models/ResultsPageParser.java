package models;

import static net.htmlparser.jericho.HTMLElementName.*;
import java.util.List;
import java.util.regex.*;
import net.htmlparser.jericho.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.*;
import com.google.common.collect.Iterables;

public class ResultsPageParser {
	private final String content;

	public ResultsPageParser(String content) {
		this.content = content;
	}

	public String getTrainNumber() {
		Source source = new Source(content);
		Element firstH2 = Iterables.getFirst(source.getAllElements(H2), null);
		Segment contentOfH2 = firstH2.getChildElements().get(0).getContent();

		return StringUtils.substringBetween(contentOfH2.toString(), "Train N°", "<");
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

	public List<TrainStationStatus> getStationDetails() {
		return null;
	}
}
