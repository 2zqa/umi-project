package shoutingMTserver;

import java.net.ServerSocket;
import java.net.Socket;

public class ShoutingMTServer {
	public static final int PORT = 7789;
	private static final int maxnrofConnections=800;
	public static TelSemafoor mijnSemafoor = new TelSemafoor(maxnrofConnections);
	
	
	public static void main(String[] args) {
		System.out.println("Running in folder "+System.getProperty("user.dir"));
		Socket connection;
		JsonGen generator = new JsonGen();
		generator.toJson();
		try {
			ServerSocket server = new ServerSocket(PORT);
			System.err.println("MT Server started..bring on the load, to a maximum of: " + maxnrofConnections);

			//System.out.println(server.toString());

			//TODO: verwijder oude bestanden d.m.v. cronjob
			
			while (true) {
				connection = server.accept();
				//System.err.println("New connection accepted..handing it over to worker thread");
				Thread worker = new Thread(new Worker(connection, generator));
				worker.start();
			}
		}

		catch (java.io.IOException ioe) {
			System.err.println("Thread is gestorven :( stacktrace:");
			ioe.printStackTrace();
		}
	}
}
