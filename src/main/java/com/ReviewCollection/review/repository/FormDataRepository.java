package com.ReviewCollection.review.repository;

import com.ReviewCollection.review.entity.FormData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormDataRepository extends JpaRepository<FormData,Long> {
    List<FormData> findAllByUserId(Long Id);
}
