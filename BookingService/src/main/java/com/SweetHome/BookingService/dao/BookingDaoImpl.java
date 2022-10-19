package com.SweetHome.BookingService.dao;

import com.SweetHome.BookingService.entity.BookingInfoEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.Date;

@Repository
public class BookingDaoImpl implements BookingDao{
    private SessionFactory sessionFactory;
    @Autowired
    public BookingDaoImpl(EntityManagerFactory entityManagerFactory){
        this.sessionFactory=entityManagerFactory.unwrap(SessionFactory.class);
    }
    @Override
    public BookingInfoEntity saveBooking(BookingInfoEntity entity) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        entity.setBookedOn(new Date());
        transaction.commit();
        session.close();
        return entity;
    }

    @Override
    public BookingInfoEntity updateBooking(BookingInfoEntity dbEntity, int intValue) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        dbEntity.setTransactionId(intValue);
        session.update(dbEntity);
        transaction.commit();
        session.close();
        return dbEntity;
    }

    @Override
    public BookingInfoEntity getBooking(int bookingId) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        BookingInfoEntity dbEntity=session.find(BookingInfoEntity.class,bookingId);
        transaction.commit();
        session.close();
        return dbEntity;
    }
}
