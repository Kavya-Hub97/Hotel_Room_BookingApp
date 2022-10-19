package com.SweetHome.BookingService.service;

import com.SweetHome.BookingService.entity.BookingInfoEntity;

import java.util.Date;

public interface BookingService {

    public BookingInfoEntity saveBooking(BookingInfoEntity entity);

    public BookingInfoEntity savePaymentDetails(String paymentMode,int bookingId,String upiId,String cardNumber,BookingInfoEntity entity);

    public BookingInfoEntity getBooking(int bookingId);
}
