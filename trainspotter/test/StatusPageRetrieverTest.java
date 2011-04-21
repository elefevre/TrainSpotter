
import static org.mockito.Mockito.*;
import java.io.*;
import java.net.MalformedURLException;
import models.*;
import org.fest.assertions.Assertions;
import org.junit.Test;
import play.test.UnitTest;

public class StatusPageRetrieverTest extends UnitTest {
	@Test
	public void shouldObtainTheStatusPageForAGivenTrainAndDay() throws MalformedURLException, IOException {
		URLConnectionProvider urlConnectionProvider = mock(URLConnectionProvider.class);
		BufferedReader mockBufferedReader = mock(BufferedReader.class);
		when(urlConnectionProvider.getInputStreamForUrl("http://www.infolignes.com/recherche.php?date_num_train=2011|04|08&num_train=7015")).thenReturn(mockBufferedReader);
		when(mockBufferedReader.readLine()).thenReturn("line1").thenReturn("line2").thenReturn(null);

		ResultsPageParser results = new StatusPageRetriever(urlConnectionProvider).downloadStatusPageForTrain("7015", 2011, 4, 8);

		Assertions.assertThat(results).isEqualTo(new ResultsPageParser("line1\nline2\n"));
	}
}
