package com.rani.autobidsystem.model;

/**
 * This is the model or POJO class to store Bidder object information.
 * It consist of constructor to create Bidder object and getter methods to read the values
 */
public class Bidder {
    //Bidder Name,In actual system this could be Person entity
    private String name;

    //BidParams are bid parameters for the current bid
    private BidParams bidParameters;

    /**
     * Constructor to initialize Bidder object
     * @param name bidder Name
     * @param bidParameters BidParameter Object
     */
    public Bidder(String name, BidParams bidParameters) {
        this.name = name;
        this.bidParameters = bidParameters;
    }

    /**
     * This method returns the bidder name
     * @return bidder name
     */
    public String getName() {
        return name;
    }

    /**
     * To read the BidParameter object
     * @returns current BidParam object
     */
    public BidParams getBidParameters() {
        return bidParameters;
    }

}
