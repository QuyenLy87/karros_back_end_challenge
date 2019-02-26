package com.example.demo.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class MetadataModel {

	private String name;

	private String desc;

	private String author;

	private Date time;
 
	private Link link;

	public MetadataModel() {

	}

	public MetadataModel(String name, String desc, String author, Date time) {
		this.name = name;
		this.desc = desc;
		this.author = author;
		this.time = time;
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

	public Link getLink() {
		return link;
	}

	@XmlElement(name = "link")
	public void setLink(Link link) {
		this.link = link;
	}

}
