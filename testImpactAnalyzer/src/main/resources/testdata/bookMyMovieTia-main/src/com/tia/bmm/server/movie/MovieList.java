package com.tia.bmm.server.movie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.tia.bmm.server.theatre.TheatreList;

public class MovieList implements Serializable {
	public List<Movie> Ml = new ArrayList<Movie>();
	static int id;
	
	static {
	    id = 1;
	}
	
	public void addMovie() {
	    Scanner sc = new Scanner(System.in);
	    String name;MovieType mt;MovieStatus ms;float r;int movt;
	    System.out.print("\nEnter name of movie: ");
	    name = sc.nextLine();
	    System.out.println("Select movie type: ");
	    System.out.println("1.Hindi\n2.English");
	    movt = Integer.parseInt(sc.nextLine());
	    switch(movt) {
	        case 1:
	            mt = MovieType.HINDI;
	            break;
	        case 2:
	            mt = MovieType.ENGLISH;
	            break;
	        default:
	            mt = MovieType.HINDI;
	    }
	
	    ms = MovieStatus.UPCOMING;
	
	    System.out.print("Enter rating(1-5 stars): ");
	    r = Float.parseFloat(sc.nextLine());
	    System.out.println("Adding movie!");
	    Movie m = new Movie(id,name,mt,ms,r);
	
	    Ml.add(m);
	
	    id++;
	}
	
	public void displayMovieList(TheatreList ob) {
        Movie m = new Movie();
        System.out.println("\n\n-------------------------------------------------");
        System.out.println("\nMOVIE DATABASE:");
        for(int i=0; i<Ml.size();i++) {
            m = Ml.get(i);
            System.out.println("\nMovieId: "+ m.getMovieId());
            System.out.println("Movie Name: " + m.getMovieName());
            System.out.println("Movie Type: "+ m.getMovieType());
            System.out.println("Movie Status: " + m.movieStatus);
            if(m.movieStatus == MovieStatus.NOW_SHOWING) {
                System.out.println("\nNOW SHOWING IN THEATRES: ");
                for(int j=0;j<m.theaterId.size();j++) {
                    ob.displayTheatre(m.theaterId.get(j));
                }
            }
            System.out.println("\nRating: " + m.getRating());
            System.out.println("----------------------------");
        }
        System.out.println("-------------------------------------------------\n");
    }
}
