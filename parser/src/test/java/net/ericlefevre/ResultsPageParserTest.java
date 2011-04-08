package net.ericlefevre;

import net.sourceforge.jwebunit.junit.WebTester;
import org.junit.Test;

public class ResultsPageParserTest {
	@Test
	public void shouldReadTheHtmlResultsPage() {
		String pathToSample = "file://" + System.getProperty("user.dir") + "/src/test/resources/net/ericlefevre";

		WebTester webTester = new WebTester();
		webTester.beginAt(pathToSample + "/sample.html");
	}
}
