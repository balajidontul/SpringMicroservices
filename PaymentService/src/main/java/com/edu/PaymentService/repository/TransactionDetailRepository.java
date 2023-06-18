package com.edu.PaymentService.repository;

import com.edu.PaymentService.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public
interface TransactionDetailRepository extends JpaRepository<TransactionDetails, Long> {
}
