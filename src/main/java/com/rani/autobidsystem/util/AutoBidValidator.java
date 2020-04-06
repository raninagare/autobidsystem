package com.rani.autobidsystem.util;

import com.rani.autobidsystem.exceptions.AutoBidException;
import com.rani.autobidsystem.model.Bid;
import com.rani.autobidsystem.model.Bidder;

/**
 * This is the validator class to validate the input
 * @author Rani Nagare
 */
public class AutoBidValidator {

    /**
     * This method validates the Bid Payload(object) and returns true if the input is valid
     * else it throws AutoBidException if there are some invalid parameters available in the input
     * @param bid : Bid object
     * @return : true if the input is valid
     * @throws AutoBidException : throws AutoBidException for invalid
     */
    public static boolean validateAuctionPayLoad(Bid bid) throws AutoBidException {
        if ( bid == null || bid.getBidders()==null||bid.getBidders().size()==0){
            throw new AutoBidException("Error in request, Please enter correct value for Bid Model.");
        }

        for(Bidder bidder :bid.getBidders()) {

            //throw exception when increment amount is negative
            if(bidder.getBidParameters().getIncrementAmount().signum()<0) {
                throw new AutoBidException("Increment amount should be positive, Please enter correct value for increment amount.");
            }
            //throw exception when maxBidPrice is less than start bid price
            if(bidder.getBidParameters().getStartingBid().compareTo(bidder.getBidParameters().getMaxBid())==1) {
                throw new AutoBidException("MaxBid amount should not be less than the start bid amount , Please enter correct value for Max Bid amount.");
            }
        }
        return true;
    }
}
