package com.SweetHome.PaymentService.dao;

import com.SweetHome.PaymentService.dto.PaymentDto;
import com.SweetHome.PaymentService.entity.TransactionDetailsEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;

@Repository
public class TransactionDaoImpl implements TransactionDao{

    private SessionFactory sessionFactory;

    @Autowired
    public TransactionDaoImpl(EntityManagerFactory entityManagerFactory){
        this.sessionFactory=entityManagerFactory.unwrap(SessionFactory.class);
    }
    @Override
    public TransactionDetailsEntity transactionDetailsFromTransactionId(int transactionId) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction= session.beginTransaction();
        TransactionDetailsEntity transactionDetailsEntity=session.find(TransactionDetailsEntity.class,transactionId);
        transaction.commit();
        session.close();
        return transactionDetailsEntity;
    }

    @Override
    public int savePaymentDetails(int bookingId, String paymentMode, String upiId, String cardNumber) {
        TransactionDetailsEntity transactionDetailsEntity=new TransactionDetailsEntity();
        transactionDetailsEntity.setBookingId(bookingId);
        transactionDetailsEntity.setPaymentMode(paymentMode);
        transactionDetailsEntity.setUpiId(upiId);
        transactionDetailsEntity.setCardNumber(cardNumber);
        Session session=this.sessionFactory.openSession();
        Transaction transaction= session.beginTransaction();
        session.save(transactionDetailsEntity);
        transaction.commit();
        return transactionDetailsEntity.getTransactionId();
    }
}
