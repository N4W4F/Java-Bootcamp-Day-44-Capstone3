package com.example.feedh.Repository;

import com.example.feedh.Model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    Rental findRentalById(Integer id);

    @Query("select r from Rental r where r.endDateTime=?1 and r.status=?2")
    List<Rental> getRentalByRentalEndDateAndStatus(LocalDateTime localDateTime, String status);
}
