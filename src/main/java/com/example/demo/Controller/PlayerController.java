package com.example.demo.Controller;

import java.util.List;

import javax.validation.Valid;

//import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Player;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.repository.TeamRepository;



@RestController
public class PlayerController {
	
	@Autowired
	PlayerRepository playerRepository;
	
	@Autowired
	TeamRepository teamRepository;

	@GetMapping("/teams/{teamId}/players")
	public List<Player> getPlayersByTeam(@PathVariable (value = "teamId") Long teamId) throws ResourceNotFoundException {
		
		return playerRepository.findByTeamId(teamId);
		
	}
	
	@PostMapping("/teams/{teamId}/players")
    public Player createPlayer(@PathVariable (value = "teamId") Long teamId,
                                 @Valid @RequestBody Player player) throws ResourceNotFoundException {
        return teamRepository.findById(teamId).map(team -> {
            player.setTeam(team);
            return playerRepository.save(player);
        }).orElseThrow(() -> new ResourceNotFoundException("Team not found"));
    }

    @PutMapping("/teams/{teamId}/players/{playerId}")
    public Player updatePlayer(@PathVariable (value = "teamId") Long teamId,
                                 @PathVariable (value = "playerId") Long playerId,
                                 @Valid @RequestBody Player playerDetails) throws ResourceNotFoundException {
        if(!teamRepository.existsById(teamId)) {
            throw new ResourceNotFoundException("TeamId " + teamId + " not found");
        }

        return playerRepository.findById(playerId).map(player -> {
            player.setFirstname(playerDetails.getFirstname());
            player.setLastname(playerDetails.getLastname());
            player.setCountry(playerDetails.getCountry());
            return playerRepository.save(player);
        }).orElseThrow(() -> new ResourceNotFoundException("Player not found"));
    }

    @DeleteMapping("/teams/{teamId}/players/{playerId}")
    public ResponseEntity<?> deletePlayer(@PathVariable (value = "teamId") Long teamId,
                              @PathVariable (value = "playerId") Long playerId) throws ResourceNotFoundException {
        return playerRepository.findByIdAndTeamId(playerId, teamId).map(player -> {
            playerRepository.delete(player);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Player not found"));
    }
	
	
}
