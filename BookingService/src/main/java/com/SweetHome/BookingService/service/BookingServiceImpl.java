package com.SweetHome.BookingService.service;

import com.SweetHome.BookingService.dao.BookingDao;
import com.SweetHome.BookingService.entity.BookingInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    BookingDao bookingDao;

    @Override
    public BookingInfoEntity saveBooking(BookingInfoEntity entity) {

        long ms = entity.getToDate().getTime()-entity.getFromDate().getTime();
        int days = (int) TimeUnit.DAYS.convert(ms, TimeUnit.MILLISECONDS);
        int roomprice=1000*days*entity.getNumOfRooms();
        entity.setRoomPrice(roomprice);
       ArrayList<String> numbersList = getRandomNumbers(entity.getNumOfRooms());
       entity.setRoomNumbers(numbersList.toString());
        bookingDao.saveBooking(entity);
        return entity;
    }


    @Override
    public BookingInfoEntity savePaymentDetails(String paymentMode, int bookingId, String upiId, String cardNumber,
                                                BookingInfoEntity dbEntity){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String,Object>> request = new HttpEntity<>(headers);
        String url="http://localhost:8083/payment/transaction";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                // Add query parameter
                .queryParam("paymentMode",paymentMode)
                .queryParam("bookingId",bookingId)
                .queryParam("upiId",upiId)
                .queryParam( "cardNumber",cardNumber);
        ResponseEntity<Integer> transactionId = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, request,Integer.class);
        String message = "Booking confirmed for user with aadhaar number: "
                + dbEntity.getAadharNumber()
                +    "    |    "
                + "Here are the booking details:    " + dbEntity.toString();
        System.out.println(message);
        return bookingDao.updateBooking(dbEntity,transactionId.getBody().intValue());
    }

    @Override
    public BookingInfoEntity getBooking(int bookingId) {
        return bookingDao.getBooking(bookingId);
    }

    public static ArrayList<String> getRandomNumbers(int count){
        Random rand = new Random();
        int upperBound = 100;
        ArrayList<String>numberList = new ArrayList<String>();

        for (int i=0; i<count; i++){
            numberList.add(String.valueOf(rand.nextInt(upperBound)));
        }
        return numberList;
    }
}
