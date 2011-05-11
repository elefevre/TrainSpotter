package services;

import static org.mockito.Mockito.*;
import java.io.*;
import java.net.MalformedURLException;
import models.*;
import org.fest.assertions.Assertions;
import org.junit.Test;
import play.test.UnitTest;

public class StatusPageRetrieverTest extends UnitTest {
	private final URLConnectionProvider urlConnectionProvider = mock(URLConnectionProvider.class);
	private final ResultsPageParser mockResultsPageParser = mock(ResultsPageParser.class);
	private final TrainInformationPage mockTrainInformationPage = mock(TrainInformationPage.class);
	private final User mockUser = mock(User.class);

	@Test
	public void shouldObtainTheStatusPageForAGivenTrainAndDay() throws MalformedURLException, IOException {
		BufferedReader bufferedReader = new BufferedReader(new StringReader("line1\nline2\n"));
		when(urlConnectionProvider.getInputStreamForUrl("http://www.infolignes.com/recherche.php?date_num_train=2011|04|08&num_train=7015")).thenReturn(bufferedReader);
		when(mockResultsPageParser.toTrainInformationPage("line1\nline2", mockUser)).thenReturn(mockTrainInformationPage);

		TrainInformationPage results = new StatusPageRetriever(urlConnectionProvider, mockResultsPageParser).downloadStatusPageForTrain("7015", 2011, 4, 8, mockUser);

		Assertions.assertThat(results).isEqualTo(mockTrainInformationPage);
	}
}
