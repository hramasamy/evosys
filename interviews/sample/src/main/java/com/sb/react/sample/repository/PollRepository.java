package com.sb.react.sample.repository;

import com.sb.react.sample.model.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    @Override
    Optional<Poll> findById(Long aLong);

    Page<Poll> findByCreatedBy(Long userId, Pageable pageable) ;
    long countByCreatedBy(Long userId) ;
    List<Poll> findByIdIn(List <Long> pollIds) ;
    List <Poll> findByIdIn(List <Long> pollIds, Sort sort) ;
}
