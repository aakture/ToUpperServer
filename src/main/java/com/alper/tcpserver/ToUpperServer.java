package com.alper.tcpserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A server that reads a line of text, and returns it uppercased. Run this as a java application,
 * and then run the the MyTCPClient to send text to this server, and get a response back. It's 
 * easiest to run this in eclipse.
 * @author aakture
 *
 */
public class ToUpperServer {

	private static final Logger log = LoggerFactory.getLogger(ToUpperServer.class);

	public static void main(String[] args) throws Exception {

		ServerSocket welcomeSocket = new ServerSocket(6789);

		while (true) {
			log.info("waiting for connection");
			Socket connectionSocket = welcomeSocket.accept();
			ToUpperServer server = new ToUpperServer();
			server.new MyHandler(connectionSocket).run();
			

		}
	}

	class MyHandler implements Runnable {
		private Socket clientSocket;

		public MyHandler(Socket socket) {
			this.clientSocket = socket;
		}

		public void run() {
			try {
				log.info("conection accepted");

				log.info("try readLine()...");
				String clientSentence = readString(clientSocket);
				log.info("Received: {}", clientSentence);
				String capitalizedSentence = clientSentence.toUpperCase();
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				writer.println(capitalizedSentence);
				writer.flush();
				writer.close();
				
				clientSocket.close();
				log.info("sent {} to client", capitalizedSentence);
			} catch (Exception ex) {
				log.error("error", ex);
			}
		}

		private String readString(Socket clientSocket) throws Exception {
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String message = reader.readLine();
			return message;
		}

	}
}
