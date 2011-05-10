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
	private final BufferedReader mockBufferedReader = mock(BufferedReader.class);
	private final ResultsPageParser mockResultsPageParser = mock(ResultsPageParser.class);
	private final TrainInformationPage mockTrainInformationPage = mock(TrainInformationPage.class);
	private final User mockUser = mock(User.class);

	@Test
	public void shouldObtainTheStatusPageForAGivenTrainAndDay() throws MalformedURLException, IOException {
		when(urlConnectionProvider.getInputStreamForUrl("http://www.infolignes.com/recherche.php?date_num_train=2011|04|08&num_train=7015")).thenReturn(mockBufferedReader);
		when(mockBufferedReader.readLine()).thenReturn("line1").thenReturn("line2").thenReturn(null);
		when(mockResultsPageParser.toTrainInformationPage("line1\nline2\n", mockUser)).thenReturn(mockTrainInformationPage);

		TrainInformationPage results = new StatusPageRetriever(urlConnectionProvider, mockResultsPageParser).downloadStatusPageForTrain("7015", 2011, 4, 8, mockUser);

		Assertions.assertThat(results).isEqualTo(mockTrainInformationPage);
	}
}
