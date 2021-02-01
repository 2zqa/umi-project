package shoutingMTserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalTime;

class Worker implements Runnable
{
	private static final int MAX_NUMBER_OF_TRIES = 200;
	private static final long MAX_DELAY_BEFORE_NEXT_TRY = 10;
	private Socket connection;
	private JsonGen generator;

	static volatile int recordInQueue = 0;

	public Worker(Socket connection, JsonGen generator) {
		this.connection = connection;
		this.generator = generator;
	}

	public void run() {
		try {
			String line = null;
			//System.err.println("New worker thread started");

			//lets check if we already accepted maximum number of connections
			ShoutingMTServer.mijnSemafoor.probeer();

			// Prepare parser
			WeatherDataXMLParser parser = new WeatherDataXMLParser();
			WeatherData data = new WeatherData();

			//begintijd voor timer
			LocalTime beginTijd = LocalTime.now();

			InputStream stream = connection.getInputStream();

			BufferedReader bin = new BufferedReader(new InputStreamReader(stream));
			int numberOfTry = 0;

			while(true) {

				if(!bin.ready()) {//If stream is not ready
					//If number of tries is not exceeded
					if (numberOfTry < MAX_NUMBER_OF_TRIES) {
						numberOfTry++;
						//Wait for stream to become ready
						Thread.sleep(MAX_DELAY_BEFORE_NEXT_TRY);
						continue;
					} else {
						break;
					}
				}
				numberOfTry = 0;
				line = bin.readLine();
				int available = stream.available();
				if(available > recordInQueue) {
					recordInQueue = available;
					System.out.println(recordInQueue);
				}
				//System.out.println(line);
//				if(9>8) {
//					throw new RuntimeException("test");
//				}
				parser.parse(line, data);

				// If it's the end of the XML file, parse it and reset buffer
				if (line.trim().equalsIgnoreCase("</MEASUREMENT>")) {
					generator.addWeatherData(data);
					data = new WeatherData();
				}
			}
			System.err.println("Client disconnected, latest line: \n"+line+"\n on thread "+Thread.currentThread().getName());

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
