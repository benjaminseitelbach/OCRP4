package com.parkit.parkingsystem.service;

import java.util.Date;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.DateUtil;

public class FareCalculatorService {


	public void calculateFare(Ticket ticket, int vehicleRegNumberCount) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		Date inDate = ticket.getInTime();
		Date outDate = ticket.getOutTime();
		double durationHours = DateUtil.getDurationHours(inDate, outDate);
		
		//FREE 30 FIRSTS MINUTES PARKING
		if (durationHours > 0.5) {
			durationHours -= 0.5;
		} else {
			durationHours = 0;
		}
		
		double price = 0;
		switch (ticket.getParkingSpot().getParkingType()) {
		case CAR: {
			price = durationHours * Fare.CAR_RATE_PER_HOUR;
			break;
		}
		case BIKE: {
			price = durationHours * Fare.BIKE_RATE_PER_HOUR;
			break;
		}
		default:
			throw new IllegalArgumentException("Unkown Parking Type");
		}

		if(vehicleRegNumberCount > 1) {
			price = price - (price * 0.05);
		}

		ticket.setPrice(price);
	}
	

}