package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.example.demo.domain.GPS;
import com.example.demo.domain.TrackPoint;
import com.example.demo.domain.WayPointTrack;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "gpx")
@JsonInclude(Include.NON_NULL)
public class GpsModel {

	@XmlTransient
	private Long id;

	@XmlElement(name = "metadata")
	private MetadataModel metadata;

	@XmlElement(name = "wpt")
	private List<WayPointTrackModel> wayPointTracks;

	@XmlElement(name = "trk")
	@JsonIgnore
	private TrackPointSequence trackPointSequence;

	@XmlTransient
	private List<TrackPointModel> trackPoints;

	public GpsModel() {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GPS convertToEntity() {
		GPS gps = new GPS();

		gps.setId(this.id);
		gps.setAuthor(this.metadata.getAuthor());
		gps.setDesc(this.metadata.getDesc());
		gps.setName(this.metadata.getName());
		gps.setTime(this.metadata.getTime());
		gps.setLinkHref(this.metadata.getLink().getHref());
		gps.setLinkText(this.metadata.getLink().getText());

		List<WayPointTrack> wayPointTrackEntities = new ArrayList();
		for (int i = 0; i < this.wayPointTracks.size(); i++) {
			WayPointTrack entity = wayPointTracks.get(i).convertToEntity();
			entity.setGps(gps);
			wayPointTrackEntities.add(entity);
		}
		gps.setWayPointTracks(wayPointTrackEntities);

		List<TrackPoint> trackPointEntities = new ArrayList();
		List<TrackPointModel> trackPointModels = this.trackPointSequence.getTrackPoints();
		for (int i = 0; i < trackPointModels.size(); i++) {
			TrackPoint entity = trackPointModels.get(i).convertToEntity();
			entity.setGps(gps);
			trackPointEntities.add(entity);
		}
		gps.setTrackPoints(trackPointEntities);

		return gps;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GpsModel convertFromEntity(GPS gps) {
		this.setId(gps.getId());
		
		MetadataModel meta = new MetadataModel(gps.getName(), gps.getDesc(), gps.getAuthor(), gps.getTime());
		Link linkType = new Link(gps.getLinkHref(), gps.getLinkText());
		meta.setLink(linkType);
		this.setMetadata(meta);

		List<WayPointTrackModel> wayPointTracks = new ArrayList();
		for (int i = 0; i < gps.getWayPointTracks().size(); i++) {
			WayPointTrack entity = gps.getWayPointTracks().get(i);
			WayPointTrackModel model = new WayPointTrackModel(entity.getId(), entity.getLat(), entity.getLon(),
					entity.getName(), entity.getSym());
			wayPointTracks.add(model);
		}
		this.setWayPointTracks(wayPointTracks);

		List<TrackPointModel> trackPoints = new ArrayList();
		for (int i = 0; i < gps.getTrackPoints().size(); i++) {
			TrackPoint entity = gps.getTrackPoints().get(i);
			TrackPointModel model = new TrackPointModel(entity.getId(), entity.getLat(), entity.getLon(),
					entity.getEle(), entity.getTime());
			trackPoints.add(model);
		}
		this.setTrackPoints(trackPoints);

		return this;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MetadataModel getMetadata() {
		return metadata;
	}

	public void setMetadata(MetadataModel metadata) {
		this.metadata = metadata;
	}

	public List<WayPointTrackModel> getWayPointTracks() {
		return wayPointTracks;
	}

	public void setWayPointTracks(List<WayPointTrackModel> wayPointTracks) {
		this.wayPointTracks = wayPointTracks;
	}

	public TrackPointSequence getTrackPointSequence() {
		return trackPointSequence;
	}

	public void setTrackPointSequence(TrackPointSequence trackPointSequence) {
		this.trackPointSequence = trackPointSequence;
	}

	public List<TrackPointModel> getTrackPoints() {
		return trackPoints;
	}

	public void setTrackPoints(List<TrackPointModel> trackPoints) {
		this.trackPoints = trackPoints;
	}

}
