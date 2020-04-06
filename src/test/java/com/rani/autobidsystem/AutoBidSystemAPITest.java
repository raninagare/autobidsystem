package com.rani.autobidsystem;

import com.rani.autobidsystem.exceptions.AutoBidException;
import com.rani.autobidsystem.model.Bid;
import com.rani.autobidsystem.model.BidParams;
import com.rani.autobidsystem.model.Bidder;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AutoBidSystemAPITest {
    Bid bid;
    List<Bidder> bidders;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testDeclareWinnerSuccessReachedAtMaxPrice() throws AutoBidException {
        bidders=new ArrayList<Bidder>(  );
        bidders.add(createBidder("Annie", new BigDecimal(50.00),new BigDecimal(80.00),new BigDecimal(3.00 )) );
        bidders.add(createBidder("Oliver", new BigDecimal(60.00),new BigDecimal(82.00),new BigDecimal(2.00 )) );
        bidders.add(createBidder("Mary", new BigDecimal(55.00),new BigDecimal(85.00),new BigDecimal(5.00 )) );

        bid = createBid("Bid 1","Shoes" , bidders);
        Bidder winner= AutoBidSystemAPI.declareWinner( bid );

        Assert.assertEquals("Bid 1", bid.getBidId());
        Assert.assertEquals("Shoes", bid.getBidProductName());
        Assert.assertEquals("Mary",winner.getName());
        Assert.assertEquals(new BigDecimal(85  ),winner.getBidParameters().getCurrentBidAmount());
    }
    @Test
    public void testDeclareWinnerSuccess_NotReachedAtMaxPrice() throws AutoBidException {

        bidders=new ArrayList<Bidder>(  );
        bidders.add(createBidder("Annie", new BigDecimal(700.00),new BigDecimal(725.00),new BigDecimal(2.00 )) );
        bidders.add(createBidder("Oliver", new BigDecimal(599.00),new BigDecimal(725.00),new BigDecimal(15.00 )) );
        bidders.add(createBidder("Mary",  new BigDecimal(625.00),new BigDecimal(725.00),new BigDecimal(8.00 )) );
        bid = createBid("Bid 2","Umbrella" , bidders);

        Bidder winner= AutoBidSystemAPI.declareWinner( bid );
        Assert.assertEquals("Annie",winner.getName());
        Assert.assertEquals(new BigDecimal(722  ),winner.getBidParameters().getCurrentBidAmount());
    }
    @Test
    public void testDeclareWinner_success() throws AutoBidException {
        bidders=new ArrayList<Bidder>(  );
        bidders.add(createBidder("Annie",  new BigDecimal(2500),new BigDecimal(3000),new BigDecimal(500 )) );
        bidders.add(createBidder("Oliver",  new BigDecimal(2800),new BigDecimal(3100),new BigDecimal(201 )) );
        bidders.add(createBidder("Mary",  new BigDecimal(2501),new BigDecimal(3200),new BigDecimal(247)) );

        bid = createBid("Bid 3","Hand Watch" , bidders);
        Bidder winner= AutoBidSystemAPI.declareWinner( bid );
        Assert.assertEquals("Oliver",winner.getName());
        Assert.assertEquals(new BigDecimal(3001  ),winner.getBidParameters().getCurrentBidAmount());
    }
    @Test
    public void testDeclareWinnerSuccessWheTwoBidderWinsThenReturnsFirst() throws AutoBidException {
         bidders=new ArrayList<Bidder>(  );
        bidders.add(createBidder("Annie", new BigDecimal(100),new BigDecimal(200),new BigDecimal(25 )) );
        bidders.add(createBidder("Oliver",  new BigDecimal(120),new BigDecimal(210),new BigDecimal(26 )) );
        bidders.add(createBidder("Mary",  new BigDecimal(150),new BigDecimal(200),new BigDecimal(50)) );

        bid = createBid("Bid 4","Hand Watch" , bidders);
        Bidder winner= AutoBidSystemAPI.declareWinner( bid );
        Assert.assertEquals("Annie",winner.getName());
        Assert.assertEquals(new BigDecimal(200  ),winner.getBidParameters().getCurrentBidAmount());
    }

    @Test
    public void testDeclareWinnerExceptionForNegativeIncrementValue() throws AutoBidException {
        bidders=new ArrayList<Bidder>(  );
        bidders.add(createBidder("Annie", new BigDecimal(100),new BigDecimal(200),new BigDecimal(-25 )) );
        bidders.add(createBidder("Oliver",  new BigDecimal(120),new BigDecimal(210),new BigDecimal(26 )) );
        bidders.add(createBidder("Mary",  new BigDecimal(150),new BigDecimal(200),new BigDecimal(50)) );
        bid = createBid("Bid 5","Hand Watch" , bidders);

        exception.expect( AutoBidException.class );
        exception.expectMessage( "Increment amount should be positive, Please enter correct value for increment amount." );
        Bidder winner= AutoBidSystemAPI.declareWinner( bid );

    }
    @Test
    public void testDeclareWinnerExceptionWhenMaxBidPriceLessThanStartBidPrice() throws AutoBidException {
        bidders=new ArrayList<Bidder>(  );
        bidders.add(createBidder("Annie", new BigDecimal(100),new BigDecimal(200),new BigDecimal(25 )) );
        bidders.add(createBidder("Oliver",  new BigDecimal(120),new BigDecimal(210),new BigDecimal(26 )) );
        bidders.add(createBidder("Mary",  new BigDecimal(150),new BigDecimal(100),new BigDecimal(50)) );
        bid = createBid("Bid 6","Hand Watch" , bidders);

        exception.expect( AutoBidException.class );
        exception.expectMessage( "MaxBid amount should not be less than the start bid amount , Please enter correct value for Max Bid amount." );

        Bidder winner= AutoBidSystemAPI.declareWinner( bid );

    }

    @Test
    public void testDeclareWinnerExceptionForNullListOfBidders() throws AutoBidException {
        bid = createBid("Bid 7","Hand Watch" , bidders);
        exception.expect( AutoBidException.class );
        exception.expectMessage( "Error in request, Please enter correct value for Bid Model" );
        Bidder winner= AutoBidSystemAPI.declareWinner( bid );
    }
    private Bid createBid(String auctionName, String productName , List<Bidder> bidders){
        bid =new Bid(auctionName,productName , bidders);
        return bid;
    }
    private Bidder  createBidder(String name,BigDecimal bidStartPrice,BigDecimal maxBidPRice,BigDecimal incrementAmount) {
        Bidder bidder=new Bidder(name, new BidParams( bidStartPrice,maxBidPRice,incrementAmount) );
        return bidder;
    }
}