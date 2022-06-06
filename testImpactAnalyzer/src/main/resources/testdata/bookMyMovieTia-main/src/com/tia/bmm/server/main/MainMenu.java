/**
 * 
 */
package com.tia.bmm.server.main;

import java.util.Scanner;

import com.tia.bmm.server.admin.Admin;
import com.tia.bmm.server.booking.MyServer;
import com.tia.bmm.server.movie.MovieList;
import com.tia.bmm.server.theatre.TheatreList;
import com.tia.bmm.server.user.UserList;

/**
 * @author aditi
 *
 */
public class MainMenu {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int choice = 0;
      
        UserList ul = new UserList();
        MovieList obj = new MovieList();
        TheatreList tl = new TheatreList();
        MyServer ms = new MyServer();
        
        do {
        	System.out.println("\nMenu:\n1. Register as User.\n2. Login as Admin.\n 3. Establish Connection with Booking Portal.");
            System.out.println("-1 to exit");
            System.out.print("Enter Choice:");
            choice = Integer.parseInt(sc.nextLine());

            switch(choice) {
                case 1:
                    ul.createUser();
                    break;

                case 2: //Admin Login and Its functionalities.
                    Admin ad = new Admin();
                    boolean flag;
                    flag = ad.loginAsAdmin();
                    if(flag) {
                        System.out.println("\nSuccessfully Logged in as Admin!!\n");
	                    int c;
	                    do {
	                        System.out.println("\nAdmin Menu:\n1. Movie Management.\n2. Theatres Management.\n3. Display registered Users.\n-1. Logout.\n");
	                        System.out.print("Enter choice: ");
	                        c = Integer.parseInt(sc.nextLine());
	
	                        switch(c) {
	                            case 1:
	                                int cho;
                                    do{
                                        System.out.println("\n\nMovie Management:\n1. Add Upcoming Movie.\n2. Display Movie database.\n-1. Return to Admin Menu.\n");
                                        System.out.print("Enter choice: ");
                                        cho = Integer.parseInt(sc.nextLine());

                                        switch(cho) {
                                            case 1:
                                                obj.addMovie();
                                                break;

                                            case 2:
                                                obj.displayMovieList(tl);
                                                break;
                                        }
                                    }while(cho!=-1);
	                                break;
	                            case 2:
	                            	int choi;
                                    do{
                                        System.out.println("\n\nTheatre Management:\n1. Add new Theatre.\n2. Remove existing Theatre.\n3. Display Theatre database.\n4. Add movies in existing theatre.\n-1. Return to Admin Menu.\n");
                                        System.out.print("Enter choice: ");
                                        choi = Integer.parseInt(sc.nextLine());

                                        switch(choi) {
                                            case 1:
                                                tl.addTheatre();
                                                break;
                                            case 2:
                                                tl.removeTheatre();
                                                break;
                                            case 3:
                                                tl.displayTheatreList();
                                                break;
                                            case 4:
                                                tl.addMovieInTheatre(obj);
                                                break;
                                        }
                                    }while(choi!=-1);
	                                break;
	
	                            case 3:
	                                ul.displayUsers();
	                                break;
	                        }
	                    }while(c!=-1);
                    }
                    break;
                case 3:
				try {
					ms.chatWithClient(ul,obj,tl);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
              }   
        }
        while(choice!=-1);   
	}
}
