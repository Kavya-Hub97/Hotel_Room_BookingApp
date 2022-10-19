package com.SweetHome.PaymentService.service;

import com.SweetHome.PaymentService.dao.TransactionDao;
import com.SweetHome.PaymentService.entity.TransactionDetailsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class TransactionDetailsImpl implements  TransactionService{
    @Autowired
    TransactionDao transactionDao;

        @Override
        public int savePaymentDetails(int bookingId, String paymentMode, String upiId, String cardNumber){
            int transactionId=transactionDao.savePaymentDetails(bookingId,paymentMode,upiId,cardNumber);
            return transactionId;
        }

        @Override
        public TransactionDetailsEntity transactionDetailsFromTransactionId(int transactionId) {
            return transactionDao.transactionDetailsFromTransactionId(transactionId);
        }



    }


