package com.tia.bmm.server.theatre;

import java.util.ArrayList;
import java.util.List;

import com.tia.bmm.server.address.Address;

import com.tia.bmm.server.movie.Movie;

public class Theatre {
	public int theatreId;
    private String theatreName;
    public Address address;
    private List<Movie> movies = new ArrayList<Movie>();

    public Theatre() {}

    Theatre(int tid,String n,Address add) {
        theatreId = tid;
        setTheatreName(n);
        address = add;
    }

	public String getTheatreName() {
		return theatreName;
	}

	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
}
