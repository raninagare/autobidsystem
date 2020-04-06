package com.rani.autobidsystem.model;

import java.util.List;

/**
 * This class is the main model class to represent the Bid object.
 * So it consist of name/id of the Bid,product name which is kept for bidding
 * and all the Bidders who are Bidding for the product
 */
public class Bid {

    private String bidId;
    private String bidProductName;
    private List<Bidder> bidders;

    /**
     * Constructor to initialize all the required parameters
     * @param bidId  This is string bid id or Name
     * @param bidProductName String product name which is kept for bidding
     * @param bidders List of bidders who participated in current bid
     */
    public Bid(String bidId, String bidProductName, List<Bidder> bidders) {
        this.bidId = bidId;
        this.bidProductName = bidProductName;
        this.bidders = bidders;
    }

    /**
     * Getter method to read bid Id
     * @return
     */
    public String getBidId() {
        return bidId;
    }

    /**
     * Getter method to read Bidding Product name
     * @return String name of the product which is placed for bidding
     */
    public String getBidProductName() {
        return bidProductName;
    }


    /**
     * Getter method to read List of all the bidders who are bidding for current bid
     * @return List<Bidder>
     */
    public List<Bidder> getBidders() {
        return bidders;
    }

}
