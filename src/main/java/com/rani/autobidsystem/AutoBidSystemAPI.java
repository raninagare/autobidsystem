package com.rani.autobidsystem;

import com.rani.autobidsystem.exceptions.AutoBidException;
import com.rani.autobidsystem.model.Bid;
import com.rani.autobidsystem.model.Bidder;
import com.rani.autobidsystem.util.AutoBidValidator;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * This class contains method which calculate winner for the given bid based on input details.
 * Application is a fail-fast, if one of the input is invalid it throws exception
 * @author Rani Nagare
 */
public class AutoBidSystemAPI {
    static Logger log = Logger.getLogger( AutoBidSystemAPI.class.getName());

    /**
     * This method takes Bid object as a input and applies algorithm to find out who is the winner in this bid
     * @return Return object is the Bidder which consist of currentBidPrice(winning price), Bidder name
     * @throws AutoBidException throws AutoBidException Exception when input is invalid(algorithm is fail-fast algorithm).
     */
     public static Bidder declareWinner(Bid bid) throws AutoBidException {

         if (!AutoBidValidator.validateAuctionPayLoad( bid )) {
             AutoBidException autoBidException =new AutoBidException("Error in request , Please correct the request");
             log.error( autoBidException );
             throw autoBidException;
         }
         boolean flag=true;
         while(flag){
           flag= incrementBidPrices( bid );
         }
         //Winner Bidder object is return
        Bidder winner= findMaxPriceBidder( bid );

         log.info( "Bid Id:"+ bid.getBidId());
         log.info("Bidding Product Name:"+ bid.getBidProductName());
         log.info("Winner Name:"+ winner.getName());
         log.info( "Bid Win Amount:"+winner.getBidParameters().getCurrentBidAmount() );
        return winner;

     }

    /**
     * This method increments the price of every bidder for one iteration except the one who has highest bidding price
     * @return false when no more increment is possible for all the bidders and
     * returns true when bid increment is done for atleast one of the bidder
     */

    private static boolean incrementBidPrices(Bid bid) {

        boolean winnerFound=false;
        //Take list ob Bidders
        List<Bidder> bidders= bid.getBidders();

        //Find the bidder who has max currentBidPrice so that we will increment the price of other bidders(if possible) except this one
        Bidder maxCurrentPriceBidder = findMaxPriceBidder( bid );

        for (Bidder bidder : bidders) {
            if (bidder != maxCurrentPriceBidder) {
                BigDecimal currentBidPrice=bidder.getBidParameters().getCurrentBidAmount();
                BigDecimal maxBidPrice=bidder.getBidParameters().getMaxBid();
                BigDecimal incrementAmount=bidder.getBidParameters().getIncrementAmount();

                BigDecimal tempBidPrice=currentBidPrice.add(incrementAmount);
                if(tempBidPrice.compareTo(maxBidPrice)<=0){
                    currentBidPrice=tempBidPrice;
                    winnerFound=true;
                }
                bidder.getBidParameters().setCurrentBidAmount( currentBidPrice );


            }
        }
        //Returns false when winner is Found
        return winnerFound;
    }

    /**
     * This method  finds the bidder who has maximum currentBid amount at the moment
     * @param bid this object consist of details about all the bidders
     * @return returns bidder who has maximum current bid amount and if 2 or more bidders have the same max current bid amount
     * it returns the first bidder among the tie(the first who made bid entry in a list)
     */
    private static Bidder findMaxPriceBidder(Bid bid) {
        List<Bidder> bidders= bid.getBidders();
        Bidder maxCurrentPriceBidder = Collections.max( bidders, Comparator.comparing( s -> s.getBidParameters().getCurrentBidAmount() ) );
        return maxCurrentPriceBidder;
    }


}
