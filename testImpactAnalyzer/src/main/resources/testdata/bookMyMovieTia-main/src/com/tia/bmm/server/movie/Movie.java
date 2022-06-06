package com.tia.bmm.server.movie;

import java.util.ArrayList;
import java.util.List;

public class Movie {
	private int movieId;
    public List<Integer> theaterId = new ArrayList<Integer>(); 
    private String movieName;
    private MovieType movieType;
    public MovieStatus movieStatus;
    private float rating;
    private List<Seat>showAtFour = new ArrayList<Seat>(50);

    public Movie() {}

    Movie(int mid,String n,MovieType mt,MovieStatus ms,float r) {
        setMovieId(mid);
        setMovieName(n);
        setMovieType(mt);
        movieStatus = ms;
        setRating(r);

        for(int i=1;i<=25;i++) {
            Seat s = new Seat(i,SeatType.SILVER,SeatStatus.SEAT_NOT_BOOKED,210);
            getShowAtFour().add(s);
        }
        for(int i=26;i<=40;i++) {
            Seat s = new Seat(i,SeatType.GOLD,SeatStatus.SEAT_NOT_BOOKED,270);
            getShowAtFour().add(s);
        }
        for(int i=40;i<=50;i++) {
            Seat s = new Seat(i,SeatType.PLATINUM,SeatStatus.SEAT_NOT_BOOKED,320);
            getShowAtFour().add(s);
        }
    }

    public void displayMovie() {
        System.out.println("\nMovieId: "+ getMovieId());
        System.out.println("Movie Name: " + getMovieName());
        System.out.println();
    }

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public List<Seat> getShowAtFour() {
		return showAtFour;
	}

	public void setShowAtFour(List<Seat> showAtFour) {
		this.showAtFour = showAtFour;
	}

	public MovieType getMovieType() {
		return movieType;
	}

	public void setMovieType(MovieType movieType) {
		this.movieType = movieType;
	}
}
