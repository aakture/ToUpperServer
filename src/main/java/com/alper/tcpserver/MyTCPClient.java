package com.alper.tcpserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple client to test the toUpper server. Reads input from the command line, and returns 
 * the upper cased response form the server. 
 * @author Alper Akture
 *
 */
public class MyTCPClient {
	private static final Logger log = LoggerFactory.getLogger(MyTCPClient.class);

	public static void main(String[] args) throws Exception {
		String sentence;
		String modifiedSentence;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		Socket clientSocket = new Socket("localhost", 6789);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		sentence = inFromUser.readLine();
		outToServer.writeBytes(sentence + '\n');
		modifiedSentence = inFromServer.readLine();
		log.info("FROM SERVER: {}", modifiedSentence);
		clientSocket.close();
	}


}
