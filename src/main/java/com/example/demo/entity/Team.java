package com.example.demo.entity;


import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="teams")
public class Team implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private long id;
	
	@Column(unique=true)
	private String name;
	
	@Column
	private String country;
	
	@Column
	private String yearFounded;
	
	@OneToMany(mappedBy = "team", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Player> players;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getYearFounded() {
		return yearFounded;
	}

	public void setYearFounded(String yearFounded) {
		this.yearFounded = yearFounded;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	
//	public List<PlayerEntity> getPlayerEntities() {
//		return playerEntities;
//	}
//
//	public void setPlayerEntities(List<PlayerEntity> playerEntities) {
//		this.playerEntities = playerEntities;
//	}

	
	
	
}
