package com.tia.bmm.server.booking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.tia.bmm.server.address.Address;
import com.tia.bmm.server.movie.Movie;
import com.tia.bmm.server.movie.MovieList;
import com.tia.bmm.server.movie.MovieStatus;
import com.tia.bmm.server.movie.Seat;
import com.tia.bmm.server.movie.SeatStatus;
import com.tia.bmm.server.theatre.Theatre;
import com.tia.bmm.server.theatre.TheatreList;

public class Booking {
	void handleBooking(MovieList ml, TheatreList tl,DataOutputStream out,DataInputStream in) throws Exception {
        int mc;
        Scanner sc = new Scanner(System.in);
        out.writeUTF("From below given Movie List, Enter Movie id for which you wish to book tickets: ");
        Movie m = new Movie();

        // ml.displayMovieList(tl);

        out.writeUTF("\n\n============================================================");
        out.writeUTF("\nMOVIE DATABASE:");
        out.writeUTF(""+ml.Ml.size());

        for(int i=0; i<ml.Ml.size();i++) {
            m = ml.Ml.get(i);
            out.writeUTF("\nMovieId: "+ m.getMovieId());
            out.writeUTF("Movie Name: " + m.getMovieName());
            out.writeUTF("Movie Type: "+ m.getMovieType());
            out.writeUTF("Movie Status: " + m.movieStatus);
            if(m.movieStatus == MovieStatus.NOW_SHOWING) out.writeUTF("NS");
            else out.writeUTF("ABS");
            if(m.movieStatus == MovieStatus.NOW_SHOWING) {
                out.writeUTF("NOW SHOWING IN THEATRES: ");
                out.writeUTF(""+m.theaterId.size());
                for(int j=0;j<m.theaterId.size();j++) {
                    //ob.displayTheatre(m.theaterId.get(j));
                    Theatre th = new Theatre();

                    for(int k=0;k<tl.Tl.size();k++) {
                        th = tl.Tl.get(k);
                        if(th.theatreId == m.theaterId.get(j)) break;
                    }
                    out.writeUTF(th.theatreId + ": " + th.getTheatreName());
                    Address a = new Address();
                    a = th.address;
                    out.writeUTF("Address: " + a.getStreetNo() + " , Near " + a.getLandmark() + " , " + a.getCity());
                    out.writeUTF("Pincode: " + a.getPinCode());
                    out.writeUTF("State: " + a.getState());
                }
            }
            out.writeUTF("\nRating: " + m.getRating());
            out.writeUTF("-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.");
        }
        out.writeUTF("============================================================\n");


        out.writeUTF("Enter MovieId for booking: ");
        mc = Integer.parseInt(in.readUTF());
        //Movie m = new Movie();

        for(int i=0;i<ml.Ml.size();i++) {
            if(ml.Ml.get(i).getMovieId() == mc) {
                m = ml.Ml.get(i);
                break;
            }
        }

        System.out.println("\nMovie id selected by client: " + mc);
        int theatreChoice,theatreIndex = 0;

        out.writeUTF("\nFrom Now Showing section of selected movie, Enter Theatre ID where you wish to watch movie: ");
        theatreChoice = Integer.parseInt(in.readUTF());
        System.out.println("\nTheatre id selected by client: " + theatreChoice);

        Theatre t = new Theatre();
        for(int i=0;i<tl.Tl.size();i++) {
            if(tl.Tl.get(i).theatreId == theatreChoice) {
                t = tl.Tl.get(i);
                theatreIndex = i;
            }
        }
        String theatreName = t.getTheatreName();

        int movieIndex = 0;
        for(int i=0;i<t.getMovies().size();i++) {
            if(t.getMovies().get(i).getMovieId() == mc) {
                m = t.getMovies().get(i);
                movieIndex = i;
            }
        }
        String movieName = m.getMovieName();

        int st,nos,temp;

        out.writeUTF("Show Timing Available: 16:00\n");
        out.writeUTF("Select seat type: 1- Silver(Rs.210) 2- Gold(Rs.270) 3- Platinum(Rs.320)\n\n");
        st = Integer.parseInt(in.readUTF());

        out.writeUTF("Enter no. of seats you wish to book: ");
        nos = Integer.parseInt(in.readUTF());

        List<Integer> sb = new ArrayList<Integer>(nos);
        Seat s = new Seat();
        int costOfTickets = 0;
        int j=0;

        switch(st) {
            case 1:
                out.writeUTF("Available seats in Silver: ");
                out.writeUTF("25");
                for(int i=0;i<25;i++) {
                    s = m.getShowAtFour().get(i);
                    if(s.getSeatStatus() == SeatStatus.SEAT_NOT_BOOKED) 
                    	out.writeUTF("YES");
                    else 
                    	out.writeUTF("NO");
                    if(s.getSeatStatus() == SeatStatus.SEAT_NOT_BOOKED)
                        out.writeUTF(s.getSeatNo() + " ");
                }
                //out.writeUTF("\nSelect " + nos + " seat nos which you wish to book: ");

                for(int i=0;i<nos;i++) {
                    temp = Integer.parseInt(in.readUTF());
                    sb.add(temp);
                }

                Collections.sort(sb);
                j = 0;
                for(int i=0;i<25;i++) {
                    s = m.getShowAtFour().get(i);
                    if(s.getSeatNo() == sb.get(j)) {
                        s.setSeatStatus(SeatStatus.SEAT_BOOKED);
                        j++;
                        m.getShowAtFour().set(i,s);
                        if(j == sb.size()) break;
                    }
                }
                costOfTickets = nos * 210;
                break;
            case 2:
                out.writeUTF("Available seats in Gold: ");
                out.writeUTF("15");
                for(int i=25;i<40;i++) {
                    s = m.getShowAtFour().get(i);
                    if(s.getSeatStatus() == SeatStatus.SEAT_NOT_BOOKED) out.writeUTF("YES");
                    else out.writeUTF("NO");
                    if(s.getSeatStatus() == SeatStatus.SEAT_NOT_BOOKED)
                        out.writeUTF(s.getSeatNo() + " ");
                }
                //out.writeUTF("\nSelect " + nos + " seat nos which you wish to book: ");
                for(int i=0;i<nos;i++) {
                    temp = Integer.parseInt(in.readUTF());
                    sb.add(temp);
                }
                Collections.sort(sb);
                j = 0;
                for(int i=25;i<40;i++) {
                    s = m.getShowAtFour().get(i);
                    if(s.getSeatNo() == sb.get(j)) {
                        s.setSeatStatus(SeatStatus.SEAT_BOOKED);
                        j++;
                        m.getShowAtFour().set(i,s);
                        if(j == sb.size()) break;
                    }
                }
                costOfTickets = nos * 270;
                break;
            case 3:
                out.writeUTF("Available seats in Platinum: ");
                out.writeUTF("10");
                for(int i=40;i<50;i++) {
                    s = m.getShowAtFour().get(i);
                    if(s.getSeatStatus() == SeatStatus.SEAT_NOT_BOOKED) out.writeUTF("YES");
                    else out.writeUTF("NO");
                    if(s.getSeatStatus() == SeatStatus.SEAT_NOT_BOOKED)
                        out.writeUTF(s.getSeatNo() + " ");
                }
                //out.writeUTF("\nSelect " + nos + " seat nos which you wish to book: ");
                for(int i=0;i<nos;i++) {
                    temp = Integer.parseInt(in.readUTF());
                    sb.add(temp);
                }
                Collections.sort(sb);
                j = 0;
                for(int i=40;i<50;i++) {
                    s = m.getShowAtFour().get(i);
                    if(s.getSeatNo() == sb.get(j)) {
                        s.setSeatStatus(SeatStatus.SEAT_BOOKED);
                        j++;
                        m.getShowAtFour().set(i,s);
                        if(j == sb.size()) break;
                    }
                }
                costOfTickets = nos * 320;
                break;
        }

        t.getMovies().set(movieIndex,m);
        tl.Tl.set(theatreIndex,t);
        out.writeUTF("\n=======================================================");
        out.writeUTF("MOVIE TICKET:");
        out.writeUTF("=======================================================\n");

        out.writeUTF("Movie Name: " + movieName);
        out.writeUTF("Theatre Name: " + theatreName);
        out.writeUTF("Seat No: ");
        if(st == 1) out.writeUTF("SILVER - ");
        else if(st == 2) out.writeUTF("GOLD - ");
        else if(st == 3) out.writeUTF("PLATINUM - ");
        out.writeUTF("" + sb.size());
        for(int i=0;i<sb.size();i++) out.writeUTF(sb.get(i) + " ");
        //out.writeUTF("\n ");
        out.writeUTF("Total cost of tickets: Rs." + costOfTickets);

        out.writeUTF("\n=======================================================\n");

    }
}
