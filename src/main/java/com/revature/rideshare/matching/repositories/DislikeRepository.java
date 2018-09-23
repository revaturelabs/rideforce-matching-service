package com.revature.rideshare.matching.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.rideshare.matching.beans.Dislike;

@Repository
@Transactional
public interface DislikeRepository extends JpaRepository<Dislike, Integer>{

	List<Dislike> findByPairUserId(int userId);
}
