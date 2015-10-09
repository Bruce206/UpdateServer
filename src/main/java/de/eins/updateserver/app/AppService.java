package de.eins.updateserver.app;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AppService {

	@Autowired
	private AppRepository appRepository;
	
	@Autowired
	private VersionRepository versionRepository;
	
	@Value("${filepath}")
	private String filepath;
	
	@Transactional
	public void update(App app) {
		App persistedApp = appRepository.findOne(app.getId());

		persistedApp.setName(app.getName());

		if (app.getVersions() != null) {
			Optional<Version> newVersion = app.getVersions().stream().filter(v -> v.getId() == null).findFirst();

			if (newVersion.isPresent()) {
				versionRepository.save(newVersion.get());
				persistedApp.getVersions().add(newVersion.get());
			}
		}
	}

	@Transactional
	public void createVersion(Long appid, String versionNumber, MultipartFile file) throws IllegalStateException, IOException {
		String path = filepath + "/files/" + appid;
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		System.out.println(file.getOriginalFilename());
		String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
		System.out.println(fileType);
		
		File persistetVersion = new File(path + "/" + versionNumber + fileType);
		file.transferTo(persistetVersion);
		
		Version version = new Version();
		version.setPathToJar(persistetVersion.getAbsolutePath());
		version.setVersionNumber(versionNumber);
		
		versionRepository.save(version);
		
		appRepository.findOne(appid).getVersions().add(version);
		
		
		System.out.println(String.format("receive %s from %s", file.getOriginalFilename(), appid));
		
	}

	@Transactional
	public void deleteApp(Long id) throws IOException {
		App app = appRepository.findOne(id);
		String path = filepath + "/files/" + app.getId();

		File versionDir = new File(path);

		if (versionDir.exists()) {
			FileUtils.deleteDirectory(versionDir);
		}
		
		appRepository.delete(id);		
	}

	
	
}
