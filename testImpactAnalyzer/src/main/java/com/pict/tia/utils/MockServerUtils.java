package com.pict.tia.utils;

import org.mockserver.integration.ClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;


public class MockServerUtils {
	
	private static ClientAndServer mockServer;
	
	public static void startMockServer() {
	    mockServer = startClientAndServer(1080);
	}
	
	public static void stopMockServer() {
	    mockServer.stop();
	}

}