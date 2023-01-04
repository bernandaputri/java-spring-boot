package id.bca7.demoSpring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.bca7.demoSpring.models.entity.BookTransaction;

@Repository
public interface TransactionRepository extends JpaRepository<BookTransaction, Integer> {
    List<BookTransaction> findByIsReturned(Boolean isReturned);
}
