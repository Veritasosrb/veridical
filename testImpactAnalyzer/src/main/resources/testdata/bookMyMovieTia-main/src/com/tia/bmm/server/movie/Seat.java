package com.tia.bmm.server.movie;

public class Seat {
	private int seatNo;
    SeatType seatType;
    private SeatStatus seatStatus;
    float seatCost;

    public Seat() {}

    Seat(int sno,SeatType st,SeatStatus ss,float c) {
        setSeatNo(sno);
        setSeatStatus(ss);
        seatType = st;
        seatCost = c;
    }

	public SeatStatus getSeatStatus() {
		return seatStatus;
	}

	public void setSeatStatus(SeatStatus seatStatus) {
		this.seatStatus = seatStatus;
	}

	public int getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(int seatNo) {
		this.seatNo = seatNo;
	}
}
