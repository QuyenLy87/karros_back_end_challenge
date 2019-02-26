package com.example.demo.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class TrackPointSequence {

	private List<TrackPointModel> trackPoints;

	public TrackPointSequence() {
	}

	public TrackPointSequence(List<TrackPointModel> trackPoints) {
		this.trackPoints = trackPoints;
	}

	@XmlElementWrapper(name = "trkseg")
	@XmlElement(name = "trkpt")
	public void setTrackPoints(List<TrackPointModel> trackPoints) {
		this.trackPoints = trackPoints;
	}

	public List<TrackPointModel> getTrackPoints() {
		return trackPoints;
	}
}
