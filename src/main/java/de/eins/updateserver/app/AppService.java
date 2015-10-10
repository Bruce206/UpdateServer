package de.eins.updateserver.app;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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

	@Transactional
	public void updateUpdater(Long appid, MultipartFile file) throws IllegalStateException, IOException {
		// create app-dir if not exists
		String path = filepath + "/files/" + appid;
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}

		// get file extension of uploaded file
		String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));

		File persistetUpdater = new File(path + "/updater" + fileType);
		file.transferTo(persistetUpdater);

		appRepository.findOne(appid).setUpdaterFilePath(persistetUpdater.getAbsolutePath());
	}

	public File getUpdater(Long id) {
		return new File(appRepository.findOne(id).getUpdaterFilePath());
	}

	public boolean checkForUpdates(String appName, String version) {
		App app = appRepository.findByName(appName);

		if (app == null || app.getVersions().isEmpty()) {
			return false;
		}

		Version latestVersion = getLatestVersion(app);

		if (latestVersion == null) {
			return false;
		}

		System.out.println(latestVersion.getVersionNumber());
		System.out.println(version);

		if (!latestVersion.getVersionNumber().equals(version)) {
			return true;
		}

		return false;
	}

	public Version getLatestVersion(App app) {
		if (!app.versions.isEmpty()) {
			Collections.sort(app.versions);
			return app.versions.get(app.versions.size() - 1);
		}
		return null;
	}

	public File getLatestApp(String appName) {
		App app = appRepository.findByName(appName);

		if (app == null || app.getVersions().isEmpty()) {
			return null;
		}

		Version latestVersion = getLatestVersion(app);

		if (latestVersion == null || latestVersion.getVersionNumber() == null) {
			return null;
		}

		return new File(latestVersion.getPathToJar());

	}

	public File getUpdaterForApp(String appName) {
		App app = appRepository.findByName(appName);

		if (app == null || StringUtils.isEmpty(app.getUpdaterFilePath())) {
			return null;
		}

		return new File(app.getUpdaterFilePath());
	}
}
