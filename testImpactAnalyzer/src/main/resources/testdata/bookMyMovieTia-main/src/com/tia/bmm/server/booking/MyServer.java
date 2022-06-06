package com.tia.bmm.server.booking;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import com.tia.bmm.server.movie.MovieList;
import com.tia.bmm.server.theatre.TheatreList;
import com.tia.bmm.server.user.User;
import com.tia.bmm.server.user.UserList;

public class MyServer {
	public void chatWithClient(UserList ul,MovieList ml, TheatreList tl) throws Exception {
        String fromClient;
        String s;
        System.out.println("\nHello!\nServer Side Booking Portal:\n");
        ServerSocket ss = new ServerSocket(6000);
        System.out.println("Server Socket awaits connections..");

        Socket cs = ss.accept();

        System.out.println("\nConnection Established with Client!\n");

        BufferedReader inFromAdmin = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(cs.getInputStream()));

        DataOutputStream outToClient = new DataOutputStream(cs.getOutputStream());
        DataInputStream din = new DataInputStream(cs.getInputStream());

        outToClient.writeBytes("WELCOME TO BookMyMovie!!\n");
        outToClient.writeBytes("Select Action: \n");
        outToClient.writeBytes("1. Login as User and Book a Ticket.\n");
        outToClient.writeBytes("2. Chat with Admin to lodge a complaint.\n");
        outToClient.writeBytes("3. Leave a Feedback.\n");

        fromClient = inFromClient.readLine();
        String str="",str2="";
        switch(fromClient) {
            case "1"://Login as User and book a ticket
                boolean flag = false;
                System.out.println("\nSelected Option: Login as User and Book a Ticket!\n");
                System.out.println("Waiting for email from client for verification.");
                String eid = din.readUTF();
                System.out.println("FROM CLIENT: Email id: " + eid);

                User u = new User();
                for(Integer i : ul.getUl().keySet()) {
                    u = ul.getUl().get(i);
                    if(u.getEmailId().equals(eid)) flag = true;
                }
                if(!flag) {
                    outToClient.writeUTF("Invalid User! Try Again!");
                }
                else {
                    outToClient.writeUTF("Login Successful!");
                    Booking b = new Booking();
                    b.handleBooking(ml,tl,outToClient,din);
                }

                break;

            case "2":
                while(!str.equals("stop")){
                    str = din.readUTF();
                    System.out.println("FROM CLIENT: " + str);

                    str2 = inFromAdmin.readLine();
                    outToClient.writeUTF(str2);
                    outToClient.flush();
                }
                break;
            case "3":
                outToClient.writeUTF("Enter your feedback: ");
                str = din.readUTF();

                System.out.println("FEEDBACK FROM CLIENT: " + str);

                outToClient.writeUTF("Thanking you for taking time to leave a feedback!");
                outToClient.writeUTF("Your feedback is valuable to us. Visit Again!");

                outToClient.flush();
                break;
        }
        din.close();
        cs.close();
        ss.close();
        //System.out.println("Choice From Client: " + fromClient);
	}
}
