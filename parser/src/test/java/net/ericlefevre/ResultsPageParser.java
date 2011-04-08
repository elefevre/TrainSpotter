package net.ericlefevre;

import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.junit.WebTester;

public class ResultsPageParser {
	private final WebTester webTester;

	public ResultsPageParser(String url) {
		webTester = new WebTester();
		webTester.beginAt(url);
	}

	public String getTrainNumber() {
		String str = "";
		for (IElement element : webTester.getElementsByXPath("//html/body")) {
			str += element.getName() + "; ";
		}
		return str;
	}

}
