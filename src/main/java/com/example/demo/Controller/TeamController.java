package com.example.demo.Controller;

import java.util.List;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Team;
import com.example.demo.exceptions.ResourceNotFoundException;

import com.example.demo.repository.TeamRepository;


@RestController
//@RequestMapping("teams")//http://localhost:8082/teams
public class TeamController {
	
	@Autowired
	TeamRepository teamRepository;

//	@GetMapping
//	public List<PlayerResponse> getAllPlayers() {
//		ModelMapper modelMapper = new ModelMapper();
//		PlayerResponse returnValue = new PlayerResponse();
//		
//	}

	@GetMapping("/teams")
	public List<Team> getAllTeams() {

	    return teamRepository.findAll();
		
	}
	
	@GetMapping("/teams/{teamId}")
    public ResponseEntity<Team> getTeamById(
        @PathVariable Long teamId) throws ResourceNotFoundException {
		
        Team foundteam = teamRepository.findById(teamId)
            .orElseThrow(() -> new ResourceNotFoundException("Team not found : " + teamId));
        
        return ResponseEntity.ok().body(foundteam);
    }
	
	@PostMapping("/teams")
	public Team createTeam(@Valid @RequestBody Team team) {
		
		return teamRepository.save(team);
		
	}
	
	
	@PutMapping("/teams/{teamId}")
	public ResponseEntity<Team> updateTeam(@PathVariable Long teamId, @Valid @RequestBody Team teamDetails) throws ResourceNotFoundException {
		
		Team foundteam = teamRepository.findById(teamId)
				.orElseThrow(() -> new ResourceNotFoundException("Team not found : " + teamId));
		

		foundteam.setName(teamDetails.getName());
		foundteam.setCountry(teamDetails.getCountry());
		foundteam.setYearFounded(teamDetails.getYearFounded());
		
		Team updatedteam = teamRepository.save(foundteam);
		
		return ResponseEntity.ok().body(updatedteam);
		
	}
	
	@DeleteMapping("/teams/{teamId}")
	public ResponseEntity<?> deleteTeam(@PathVariable Long teamId) throws ResourceNotFoundException {
		
		Team foundteam = teamRepository.findById(teamId)
				.orElseThrow(() -> new ResourceNotFoundException("Team not found : " + teamId));
		
		
        teamRepository.delete(foundteam);
         
        return ResponseEntity.ok().build();
       
	}
}