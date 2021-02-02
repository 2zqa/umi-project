package shoutingMTserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalTime;

class Worker implements Runnable
{
	private static final int MAX_QUEUE_SIZE = 10000; // room for about 3 xml files(3K bytes) + margin
	private Socket connection;
	private JsonGen generator;

	public Worker(Socket connection, JsonGen generator) {
		this.connection = connection;
		this.generator = generator;
	}

	public void run() {
		try {
			//lets check if we already accepted maximum number of connections
			ShoutingMTServer.mijnSemafoor.probeer();

			// Prepare parser
			WeatherDataXMLParser parser = new WeatherDataXMLParser();
			WeatherData data = new WeatherData();

			InputStream stream = connection.getInputStream();
			BufferedReader bin = new BufferedReader(new InputStreamReader(stream));
			String line = null;
			String lastLine = "";
			while((line = bin.readLine()) != null) {
				lastLine = line;

				// A problem was that the program could not keep up with the incoming data. To migitate this, we clear
				// the queue when it becomes too full. This is done by reading/clearing the lines as fast as possible.
				int available = stream.available();
				if(available > MAX_QUEUE_SIZE) {
					// Throw away the now unusable weatherdata
					data = new WeatherData();

					// clear queue from bufferedReader (and therefore InputStream)
					while(stream.available() > 0 && bin.ready()) {
						bin.readLine();
					}
					//System.out.println("Buffer was too large; cleared.");
				}

				// Parse data
				parser.parse(line, data);

				// If it's the end of the XML file, parse it and reset buffer
				if (line.contains("</MEASUREMENT>")) {
					generator.addWeatherData(data);
					data = new WeatherData();
				}
			}
			System.err.println("Client disconnected, latest line: \n"+lastLine+"\n on thread "+Thread.currentThread().getName());

			// now close the socket connection
			connection.close();
			//System.err.println("Connection closed: workerthread ending");
			// upping the semaphore.. since the connnection is gone....
			ShoutingMTServer.mijnSemafoor.verhoog();
		}
		catch (IOException | InterruptedException ioe) {
			ioe.printStackTrace();
		}
	}
}
