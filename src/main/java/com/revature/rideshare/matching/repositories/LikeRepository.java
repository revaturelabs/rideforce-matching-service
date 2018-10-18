package com.revature.rideshare.matching.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.rideshare.matching.beans.Like;

/**
 * The Interface LikeRepository.
 */
@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

	List<Like> findByPairUserId(int userId);
}
