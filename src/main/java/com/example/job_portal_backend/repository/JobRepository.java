package com.example.job_portal_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.job_portal_backend.entity.Job;
import com.example.job_portal_backend.entity.User;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByTitleContainingIgnoreCase(String keyword);
    List<Job> findByEmployerId(Long employerId);
    List<Job> findByEmployer(User employer);


    @Query("SELECT j FROM Job j WHERE " +
       "(:keyword IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
       "(:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
       "(:category IS NULL OR LOWER(j.category) = LOWER(:category)) AND " +
       "(:contractType IS NULL OR LOWER(j.contractType) = LOWER(:contractType)) AND " +
       "(:experienceLevel IS NULL OR j.experienceLevel = :experienceLevel)")
    List<Job> searchJobs(
        @Param("keyword") String keyword,
        @Param("location") String location,
        @Param("category") String category,
        @Param("contractType") String contractType,
        @Param("experienceLevel") Integer experienceLevel
    );


}


