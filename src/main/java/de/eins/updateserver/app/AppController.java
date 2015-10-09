package de.eins.updateserver.app;

import java.io.IOException;

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
@RequestMapping("/api")
public class AppController {

	@Autowired
	private AppRepository appRepository;

	@Autowired
	private AppService appService;

	@RequestMapping(value = "app", method = RequestMethod.GET)
	public Iterable<App> get() {
		return appRepository.findAll();
	}

	@RequestMapping(value = "/app/{id}", method = RequestMethod.GET)
	public App getOne(@PathVariable Long id) {
		return appRepository.findOne(id);
	}

	@RequestMapping(value = "/app/{id}", method = RequestMethod.POST)
	public void updateOne(@PathVariable Long id, @RequestBody App app) {
		appService.update(app);

	}

	@RequestMapping(value = "/app", method = RequestMethod.POST)
	public void updateOne(@RequestBody App app) {
		appRepository.save(app);
	}

	@RequestMapping(value = "/app/{id}", method = RequestMethod.DELETE)
	public void deleteOne(@PathVariable Long id) throws IOException {
		appService.deleteApp(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/newversion/{appid}")
	public void upload(@PathVariable Long appid, @RequestParam String versionNumber, @RequestParam("file") MultipartFile file) throws IOException {
		appService.createVersion(appid, versionNumber, file);
	}

}
