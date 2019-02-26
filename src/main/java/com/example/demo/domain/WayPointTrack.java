package com.example.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class WayPointTrack {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "lat")
	private String lat;
	
	@Column(name = "lon")
	private String lon;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "sym")
	private String sym;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gps_id")
	private GPS gps;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSym() {
		return sym;
	}

	public void setSym(String sym) {
		this.sym = sym;
	}

	public GPS getGps() {
		return gps;
	}

	public void setGps(GPS gps) {
		this.gps = gps;
	}
}
