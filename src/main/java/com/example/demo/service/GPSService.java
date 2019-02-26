package com.example.demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.GPSRepository;
import com.example.demo.domain.GPS;
import com.example.demo.model.GpsModel;
import com.example.demo.model.Link;
import com.example.demo.model.MetadataModel;

@Service
public class GPSService {

	private final static String[] ALLOWED_EXTESION = { "kml", "gpx", "plt", "gdb" };

	@Autowired
	private GPSRepository gpsRepository;

	public void insertData(MultipartFile multipart)
			throws IOException, InvalidFileNameException, DataIntegrityViolationException, JAXBException {
		boolean isFileValid = validateInputFile(multipart);

		if (isFileValid) {
			GpsModel gps = unmarshalFile(multipart);
			GPS gpsEntity = gps.convertToEntity();
			gpsRepository.save(gpsEntity);
		}
	}

	private boolean validateInputFile(MultipartFile multipart) {
		String fileExtension = FilenameUtils.getExtension(multipart.getOriginalFilename());
		if (!Arrays.stream(ALLOWED_EXTESION).anyMatch(fileExtension::equals)) {
			throw new InvalidFileNameException(multipart.getOriginalFilename(), "Invalid File extension");
		}

		return true;
	}

	private GpsModel unmarshalFile(MultipartFile multipart)
			throws IOException, InvalidFileNameException, JAXBException {
		File file = convertToFile(multipart);

		JAXBContext jaxbContext = JAXBContext.newInstance(GpsModel.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		GpsModel gps = (GpsModel) unmarshaller.unmarshal(file);

		return gps;
	}

	private File convertToFile(MultipartFile multipart) throws IOException {
		File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipart.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(multipart.getBytes());
		fos.close();
		return convFile;
	}

	public Page<GpsModel> getLatestTracks(int page, int size) {
		Page<GPS> result = gpsRepository.findAll(new PageRequest(page, size, Direction.DESC, "time"));

		List<GPS> gpsList = result.getContent();
		List<GpsModel> gpsModels = new ArrayList<>();
		for (int i = 0; i < gpsList.size(); i++) {
			GPS entity = gpsList.get(i);
			GpsModel gpsModel = new GpsModel();
			gpsModel.setId(entity.getId());
			MetadataModel metadatModel = new MetadataModel(entity.getName(), entity.getDesc(), entity.getAuthor(),
					entity.getTime());
			Link link = new Link(entity.getLinkHref(), entity.getLinkText());
			metadatModel.setLink(link);
			gpsModel.setMetadata(metadatModel);

			gpsModels.add(gpsModel);
		}
		return new PageImpl<>(gpsModels, new PageRequest(page, size), result.getTotalElements());
	}

	@Transactional(readOnly = true)
	public GpsModel getGPSDetail(long id) {
		GpsModel model = new GpsModel();

		GPS result = gpsRepository.findOne(id);
		if (result != null) {
			return model.convertFromEntity(result);
		}

		return model;
	}

}
