package com.rani.autobidsystem.model;

import java.math.BigDecimal;

/**
 * This class is a Model or POJO class to store bidding related data
 * @author Rani Nagare
 */
public class BidParams {

    private BigDecimal startingBid;
    private BigDecimal maxBid;
    private BigDecimal incrementAmount;

    /* CurrentBidAmount is the price after incrementing the startingBid. At the end of the bid ,
    this field contains what was the max amount this bidder had offered for the current bid or to buy  current product
    */
    private BigDecimal currentBidAmount;

    /**
     * Constructor to intilize BidParam object
     * @param startingBid
     * @param maxBid
     * @param incrementAmount
     */
    public BidParams(BigDecimal startingBid, BigDecimal maxBid, BigDecimal incrementAmount) {
        this.startingBid = startingBid;
        this.maxBid = maxBid;
        this.incrementAmount = incrementAmount;
        this.currentBidAmount=startingBid;
    }

    /**
     * Getter method to read starting Bid Price for the current bidder
     * @return BigDecimal value starting bid price ,the current bidder has offered
     */
    public BigDecimal getStartingBid() {
        return startingBid;
    }

    /**
     * Getter method to read max Bid Price for the current bidder for current bid
     * @return BigDecimal value max bid price ,the current bidder has placed
     */
    public BigDecimal getMaxBid() {
        return maxBid;
    }

    /**
     * Getter method to read increment amount for the current bidder for current bid
     * @return BigDecimal value increment amount ,the current bidder has placed
     */
    public BigDecimal getIncrementAmount() {
        return incrementAmount;
    }

    /**
     * Setter method to set the price. As after every move we are changing the current price so we use this method
     * @param currentBidAmount
     */

    public void setCurrentBidAmount(BigDecimal currentBidAmount) {
        this.currentBidAmount = currentBidAmount;
    }

    /**
     * Getter method to read current Bid amount after incrementing it by incrementAmount to win the current bid
     * @return BigDecimal value current bid amount ,it is calculated after incementing starting bid price by increment amount
     */
    public BigDecimal getCurrentBidAmount() {
        return currentBidAmount;
    }
}
