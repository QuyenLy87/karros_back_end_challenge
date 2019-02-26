package com.example.demo.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.example.demo.domain.WayPointTrack;

public class WayPointTrackModel {
	
	@XmlTransient
	private Long id;
	
	private String lat;
	
	private String lon;
	
	private String name;
	
	private String sym;

	public WayPointTrackModel() {}
	
	public WayPointTrackModel (Long id, String lat, String lon, String name, String sym) {
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.name = name;
		this.sym = sym;
	}
	
	public WayPointTrack convertToEntity() {
		WayPointTrack entity = new WayPointTrack();
		
		entity.setId(this.id);
		entity.setLat(this.lat);
		entity.setLon(this.lon);
		entity.setName(this.name);
		entity.setSym(this.sym);
		
		return entity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLat() {
		return lat;
	}

	@XmlAttribute(name="lat")
	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	@XmlAttribute(name = "lon")
	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getName() {
		return name;
	}

	@XmlElement(name = "name")
	public void setName(String name) {
		this.name = name;
	}

	public String getSym() {
		return sym;
	}

	@XmlElement(name = "sym")
	public void setSym(String sym) {
		this.sym = sym;
	}
}
