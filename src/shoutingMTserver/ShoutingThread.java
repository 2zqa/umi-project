package shoutingMTserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalTime;

class Worker implements Runnable
{
	private Socket connection;
	private JsonGen generator;

	public Worker(Socket connection, JsonGen generator) {
		this.connection = connection;
		this.generator = generator;
	}

	public void run() {
		try {
			String line;
			//System.err.println("New worker thread started");

			//lets check if we already accepted maximum number of connections
			ShoutingMTServer.mijnSemafoor.probeer();

			// Prepare parser
			WeatherDataXMLParser parser = new WeatherDataXMLParser();
			WeatherData data = new WeatherData();

			//begintijd voor timer
			LocalTime beginTijd = LocalTime.now();

			BufferedReader bin = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = bin.readLine()) != null) {
				//System.out.println(line);
				parser.parse(line, data);

				// If it's the end of the XML file, parse it and reset buffer
				if(line.trim().equalsIgnoreCase("</MEASUREMENT>")) {
					generator.addWeatherData(data);
					data = new WeatherData();
				}
			}

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
