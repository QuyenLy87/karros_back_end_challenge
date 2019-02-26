package com.example.demo.domain;

import java.util.Date;
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
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"author", "time"})})
public class GPS {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "desc", columnDefinition = "TEXT")
	private String desc;

	@Column(name = "author")
	private String author;

	@Column(name = "time")
	private Date time;
	
	@Column(name = "linkHref")
	private String linkHref;
	
	@Column(name = "linkText")
	private String linkText;

	@OneToMany(mappedBy = "gps", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<TrackPoint> trackPoints;

	@OneToMany(mappedBy = "gps", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<WayPointTrack> wayPointTracks;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getLinkHref() {
		return linkHref;
	}

	public void setLinkHref(String linkHref) {
		this.linkHref = linkHref;
	}

	public String getLinkText() {
		return linkText;
	}

	public void setLinkText(String linkText) {
		this.linkText = linkText;
	}

	public List<TrackPoint> getTrackPoints() {
		return trackPoints;
	}

	public void setTrackPoints(List<TrackPoint> trackPoints) {
		this.trackPoints = trackPoints;
	}

	public List<WayPointTrack> getWayPointTracks() {
		return wayPointTracks;
	}

	public void setWayPointTracks(List<WayPointTrack> wayPointTracks) {
		this.wayPointTracks = wayPointTracks;
	}

}
