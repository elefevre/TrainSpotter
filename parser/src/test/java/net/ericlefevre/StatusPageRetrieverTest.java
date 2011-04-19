package net.ericlefevre;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;
import java.io.*;
import java.net.MalformedURLException;
import org.junit.Test;

public class StatusPageRetrieverTest {
	@Test
	public void shouldObtainTheStatusPageForAGivenTrainAndDay() throws MalformedURLException, IOException {
		URLConnectionProvider urlConnectionProvider = mock(URLConnectionProvider.class);
		BufferedReader mockBufferedReader = mock(BufferedReader.class);
		when(urlConnectionProvider.getInputStreamForUrl("http://www.infolignes.com/recherche.php?date_num_train=2011|04|08&num_train=7015")).thenReturn(mockBufferedReader);
		when(mockBufferedReader.readLine()).thenReturn("line1").thenReturn("line2").thenReturn(null);

		String results = new StatusPageRetriever(urlConnectionProvider).downloadStatusPageForTrain("7015", 2011, 4, 8);

		assertThat(results).isEqualTo("line1\nline2\n");
	}
}
