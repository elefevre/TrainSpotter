package services;

import java.io.*;
import java.net.*;

public class URLConnectionProvider {

	public BufferedReader getInputStreamForUrl(String url) throws MalformedURLException, IOException {
		InputStream inputStream = new URL(url).openConnection().getInputStream();
		return new BufferedReader(new InputStreamReader(inputStream));
	}

}
