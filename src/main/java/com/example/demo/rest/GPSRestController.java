package com.example.demo.rest;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.GpsModel;
import com.example.demo.service.GPSService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@Api("GPS Controller")

@RestController
public class GPSRestController {

	@Autowired
	private GPSService gpsService;

	@ApiOperation(value = "Insert GPS track")
	@ApiResponse(code = 201, message = "Created")
	@RequestMapping(value = "/gps", method = RequestMethod.POST, produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		try {
			gpsService.insertData(file);
		} catch (DataIntegrityViolationException ex) {
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
		} catch (JAXBException | InvalidFileNameException ex ) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@ApiOperation(value = "Get list of \"Latest track\"")
	@ApiResponse(code = 200, message = "OK")
	@RequestMapping(value = "/gps/lastest-tracks", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Page<GpsModel> getLatestTracks(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "50") int size) {
		return gpsService.getLatestTracks(page, size);
	}

	@ApiOperation(value = "View details of gpx file")
	@ApiResponse(code = 200, message = "OK")
	@RequestMapping(value = "/gps/{id}/detail", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public GpsModel getGPSDetail(@PathVariable("id") long id) {
		return gpsService.getGPSDetail(id);
	}
}
