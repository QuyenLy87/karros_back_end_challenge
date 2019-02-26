package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;

import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.GpsModel;
import com.example.demo.service.GPSService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class GPSServiceTests {

	@Autowired
	private GPSService gpsService;

	@Test(expected = InvalidFileNameException.class)
	public void testUploadFileWithInvalidFileExtension()
			throws DataIntegrityViolationException, InvalidFileNameException, IOException, JAXBException {
		MultipartFile multipartFile = getFile("invalid-file-extension.txt");

		assertEquals("invalid-file-extension.txt", multipartFile.getOriginalFilename());
		gpsService.insertData(multipartFile);
	}

	@Test(expected = UnmarshalException.class)
	public void testUploadFileWithInvalidFileFormat()
			throws DataIntegrityViolationException, InvalidFileNameException, IOException, JAXBException {
		MultipartFile multipartFile = getFile("invalid-file-format.gpx");

		assertEquals("invalid-file-format.gpx", multipartFile.getOriginalFilename());
		gpsService.insertData(multipartFile);
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void testUploadFileWitDuplicated()
			throws DataIntegrityViolationException, InvalidFileNameException, IOException, JAXBException {
		MultipartFile multipartFile = getFile("duplicated.gpx");

		assertEquals("duplicated.gpx", multipartFile.getOriginalFilename());
		gpsService.insertData(multipartFile);
		gpsService.insertData(multipartFile);
	}

	@Test
	public void testUploadFileWithoutError()
			throws DataIntegrityViolationException, InvalidFileNameException, IOException, JAXBException {
		MultipartFile multipartFile = getFile("sample.gpx");

		assertEquals("sample.gpx", multipartFile.getOriginalFilename());
		gpsService.insertData(multipartFile);

		Page<GpsModel> result = gpsService.getLatestTracks(0, 10);
		assertTrue(result.getContent().size() == 1);
	}
	
	@Test
	public void tesGetLatestTracks()
			throws DataIntegrityViolationException, InvalidFileNameException, IOException, JAXBException {
		MultipartFile multipartFile = getFile("sample.gpx");

		assertEquals("sample.gpx", multipartFile.getOriginalFilename());
		gpsService.insertData(multipartFile);

		Page<GpsModel> result = gpsService.getLatestTracks(0, 10);
		assertTrue(result.getContent().size() == 1);
		
		MultipartFile multipartFile1 = getFile("sample2.gpx");
		
		assertEquals("sample2.gpx", multipartFile1.getOriginalFilename());
		gpsService.insertData(multipartFile1);

		result = gpsService.getLatestTracks(0, 10);
		assertTrue(result.getContent().size() == 2);
		
		
		GpsModel item0 = result.getContent().get(0);
		GpsModel item1 = result.getContent().get(1);
		assertTrue(item0.getMetadata().getTime().compareTo(item1.getMetadata().getTime()) > 0);
	}

	@Test
	public void tesGetTrackDetail()
			throws DataIntegrityViolationException, InvalidFileNameException, IOException, JAXBException {
		MultipartFile multipartFile = getFile("sample.gpx");

		assertEquals("sample.gpx", multipartFile.getOriginalFilename());
		gpsService.insertData(multipartFile);

		Page<GpsModel> result = gpsService.getLatestTracks(0, 10);
		assertTrue(result.getContent().size() == 1);
		
		long trackId = result.getContent().get(0).getId();
		GpsModel gpsModel = gpsService.getGPSDetail(trackId);
		
		assertTrue(gpsModel.getId() == trackId);
		assertTrue(gpsModel.getTrackPoints().size() != 0);
		assertTrue(gpsModel.getWayPointTracks().size() != 0);
	}
	
	private MultipartFile getFile(String fileName) {
		Path path = Paths.get("test-data/" + fileName);
		String contentType = "text/plain";
		byte[] content = null;
		try {
			content = Files.readAllBytes(path);
		} catch (final IOException e) {
		}
		MultipartFile result = new MockMultipartFile(fileName, fileName, contentType, content);

		return result;
	}

}
