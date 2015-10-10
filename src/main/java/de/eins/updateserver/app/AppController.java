package de.eins.updateserver.app;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/app")
public class AppController {

	@Autowired
	private AppRepository appRepository;

	@Autowired
	private AppService appService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<App> get() {
		return appRepository.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public App getOne(@PathVariable Long id) {
		return appRepository.findOne(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public void updateOne(@PathVariable Long id, @RequestBody App app) {
		// nothing to do
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public App updateOne(@RequestBody App app) {
		return appRepository.save(app);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteOne(@PathVariable Long id) throws IOException {
		appService.deleteApp(id);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/{id}/newversion", method = RequestMethod.POST)
	public void upload(@PathVariable Long id, @RequestParam String versionNumber, @RequestParam("file") MultipartFile file) throws IOException {
		appService.createVersion(id, versionNumber, file);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/{id}/updater", method = RequestMethod.POST)
	public void uploadUpdater(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
		appService.updateUpdater(id, file);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/{id}/updater")
	public File getUpdater(@PathVariable Long id) throws IOException {
		return appService.getUpdater(id);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/check/{name}/{version:.+}")
	public List<File> getUpdater(@PathVariable String name, @PathVariable String version) throws IOException {
		return appService.checkForUpdates(name, version);
	}

}
