package com.example.demo.repository;

import java.util.List;
//import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

	List<Player> findByTeamId(Long teamId);
	Optional<Player> findByIdAndTeamId(Long id, Long teamId);
	
}
