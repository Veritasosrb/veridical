package com.tia.bmm.server.theatre;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.tia.bmm.server.address.Address;
import com.tia.bmm.server.movie.Movie;
import com.tia.bmm.server.movie.MovieList;
import com.tia.bmm.server.movie.MovieStatus;

public class TheatreList {
	public List<Theatre> Tl = new ArrayList<Theatre>();
    static int id;

    static{
        id = 1;
    }

    public void addTheatre() {
        String name;
        Scanner sc = new Scanner(System.in);
        Address a = new Address();
        System.out.println("\n\nEnter Theatre Details: ");
        System.out.print("Name of Theatre: ");
        name = sc.nextLine();
        System.out.println("Address Details: ");
        a = a.addAddress();

        Theatre t = new Theatre(id,name,a);
        id++;

        Tl.add(t);
    }

    public void removeTheatre() {
        Scanner sc = new Scanner(System.in);
        int tid;

        System.out.print("Enter TheatreId of Theatre to be removed: ");
        tid = Integer.parseInt(sc.nextLine());
        Theatre t =new Theatre();
        int index = 0;
        for(int i=0;i<Tl.size();i++) {
            t = Tl.get(i);
            if(t.theatreId == tid) {
                index = i;
                break;
            }
        }
        Tl.remove(index);
    }

    public void displayTheatreList() {
        Theatre t = new Theatre();
        Address a = new Address();
        //System.out.println(Ml);
        System.out.println("\n\n-------------------------------------------------");
        System.out.println("\nTHEATRE DATABASE:");
        for(int i=0; i<Tl.size();i++) {
            t = Tl.get(i);
            System.out.println("\nTheatreId: "+ t.theatreId);
            System.out.println("Theatre Name: " + t.getTheatreName());
            a = t.address;
            a.displayAddress(a);
            System.out.println();
        }
        System.out.println("-------------------------------------------------\n");
    }

    public void addMovieInTheatre(MovieList obj) {
        int tid;
        Scanner sc = new Scanner(System.in);

        displayTheatreList();

        System.out.print("From above Theatre Database, Enter TheatreId of Theatre you want to insert movies in: ");
        tid = Integer.parseInt(sc.nextLine());

        if(checkTheatreIdPresent(tid)) {
            Theatre t = new Theatre();
            int theatreIndex = 0;
            for(int i=0;i<Tl.size();i++) {
                t = Tl.get(i);
                if(t.theatreId == tid){
                    theatreIndex = i;
                    break;
                }
            }
            Movie m = new Movie();
            
            System.out.println("\nFor given movies, Enter: \n1.To add as Upcoming Movie.\n2.To add as Now Showing.\n3. Not Applicable.");
            int mc;

            for(int i=0;i< obj.Ml.size();i++) {
                m = obj.Ml.get(i);

                m.displayMovie();

                System.out.print("\nEnter choice(1-Uc , 2-Ns , 3-N/A): ");
                mc = Integer.parseInt(sc.nextLine());

                if(mc == 1) {
                    t.getMovies().add(m);
                    Tl.set(theatreIndex,t);
                }
                else if(mc==2) {
                    m.movieStatus = MovieStatus.NOW_SHOWING;
                    m.theaterId.add(tid);
                    //System.out.println("theaterId arraylist: " + m.theaterId);
                    t.getMovies().add(m);
                    obj.Ml.set(i,m);
                    Tl.set(theatreIndex,t);
                    //System.out.println(obj.Ml.get(i).theaterId);
                }
            }

            displayMoviesInTheatre(t);
        }
        else {
            System.out.println("Theatre does not exist in database!");
        }
    }

    //Add method to remove movie from database

    void displayMoviesInTheatre(Theatre t) {
        Movie m = new Movie();
        System.out.println("\n=======================================================");
        System.out.println("Now Showing Movies in " + t.getTheatreName() + " are: ");
        System.out.println("=======================================================");

        for(int i=0;i<t.getMovies().size();i++) {
            m = t.getMovies().get(i);
            if(m.movieStatus == MovieStatus.NOW_SHOWING) {
                m.displayMovie();
            }
        }
        System.out.println("\n=======================================================");
        System.out.println("Upcoming Movies in " + t.getTheatreName() + " are: ");
        System.out.println("=======================================================");

        for(int i=0;i<t.getMovies().size();i++) {
            m = t.getMovies().get(i);
            if(m.movieStatus == MovieStatus.UPCOMING) {
                m.displayMovie();
            }
        }
    }

    boolean checkTheatreIdPresent(int t) {
        Theatre th = new Theatre();

        for(int i=0;i<Tl.size();i++) {
            th = Tl.get(i);
            if(th.theatreId == t) return true;
        }
        return false;
    }


    public void displayTheatre(int t) {
        Theatre th = new Theatre();

        for(int i=0;i<Tl.size();i++) {
            th = Tl.get(i);
            if(th.theatreId == t) break;
        }

        System.out.println(th.theatreId + ": " + th.getTheatreName());
        Address a = new Address();
        a = th.address;
        a.displayAddress(a);
        System.out.println();
    }
}
