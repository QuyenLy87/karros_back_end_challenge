package com.example.demo.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.example.demo.domain.TrackPoint;

public class TrackPointModel {

	@XmlTransient
	private Long id;
	
	private String lat;

	private String lon;

	private String ele;

	private Date time;

	public TrackPointModel() {
	}

	public TrackPointModel(Long id, String lat, String lon, String ele, Date time) {
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.ele = ele;
		this.time = time;
	}

	public TrackPoint convertToEntity() {
		TrackPoint entity = new TrackPoint();
		entity.setId(this.id);
		entity.setLat(this.lat);
		entity.setLon(this.lon);
		entity.setEle(this.ele);
		entity.setTime(this.time);
		
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

	@XmlAttribute(name = "lat")
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

	public String getEle() {
		return ele;
	}

	@XmlElement(name = "ele")
	public void setEle(String ele) {
		this.ele = ele;
	}

	public Date getTime() {
		return time;
	}

	@XmlElement(name = "time")
	public void setTime(Date time) {
		this.time = time;
	}
	
}
