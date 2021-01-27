package shoutingMTserver;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.net.*;
import java.io.*;
import java.time.LocalTime;
//import java.util.concurrent.*;

class Worker implements Runnable
{
	private Socket connection;

	public Worker(Socket connection) {
		this.connection = connection;
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

			// Json generator aanmaken
			JsonGen jsonGen = new JsonGen();

			//begintijd voor timer
			LocalTime beginTijd = LocalTime.now();

			BufferedReader bin = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = bin.readLine()) != null) {
				//System.out.println(line);
				parser.parse(line, data);

				// If it's the end of the XML file, parse it and reset buffer
				if(line.trim().equalsIgnoreCase("</MEASUREMENT>")) {
					jsonGen.addWeatherData(data);
					System.out.println(data);
					data = new WeatherData();
				}
				//elke 60 seconden wordt de data in de jsongen naar een json-bestand geschreven
				if(LocalTime.now().getMinute() - beginTijd.getMinute() >=1) {
					System.out.println("");
					System.out.println("Data moet nu in file komen");
					System.out.println("");
					// json bestand maken en de generator legen BELANGRIJK: zoals de data nu wordt opgeslagen is niet handig, moet nog ff nadenken over naamgeving
					jsonGen.toJson("jsondump\\minuut" + LocalTime.now().getMinute() + ".json");
					jsonGen.removeAllData();
					//timer resetten
					beginTijd = LocalTime.now();
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
