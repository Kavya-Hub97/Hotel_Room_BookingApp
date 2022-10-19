package com.SweetHome.BookingService.dao;

import com.SweetHome.BookingService.entity.BookingInfoEntity;

public interface BookingDao {
    public BookingInfoEntity saveBooking(BookingInfoEntity entity);

    BookingInfoEntity updateBooking(BookingInfoEntity entity, int intValue);

    public BookingInfoEntity getBooking(int bookingId);
}
