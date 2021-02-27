package com.parkit.parkingsystem.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Period;

import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.dao.TicketDAO;


public class FareCalculatorService {
	
	TicketDAO ticketDAO;

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        long inHour = ticket.getInTime().getTime();
        System.out.println("heure d'entrée:" + inHour);
        long outHour = ticket.getOutTime().getTime();
        System.out.println("heure de sortie:" + outHour);

        //TODO: Some tests are failing here. Need to check if this logic is correct
        
        double duration = (outHour - inHour )/(60.0*60.0 * 1000.0);
        System.out.println("durée:"+ duration);
        //duration = duration - 0.5;
        if (duration < 0.5) {
        	ticket.setPrice(0);
        }	else {
 
        ticketDAO = new TicketDAO();
	      if(ticketDAO.KnownVehicle(ticket)) 
	    	  
	      {
	    	  System.out.println("Welcome back! As a recurring user of our parking lot, you'll benefit from a 5% discount.");
	    	  duration = duration * 0.95;
	      }
      
	      switch (ticket.getParkingSpot().getParkingType()){
	        case CAR: {
	            ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
	            break;
	        }
	        case BIKE: {
	            ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
	            break;
	        }
	        
	        default: throw new IllegalArgumentException("Unkown Parking Type");
	      }
        }
      
        
    }
   
}


