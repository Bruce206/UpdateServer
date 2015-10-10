package de.eins.updateserver.app;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateController {

	@Autowired
	AppService appService;

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/check/{name}/{version:.+}")
	public boolean checkUpdate(@PathVariable String name, @PathVariable String version) throws IOException {
		return appService.checkForUpdates(name, version);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/download/{name}")
	public FileSystemResource getApp(@PathVariable String name) throws IOException {
		return new FileSystemResource(appService.getLatestApp(name));
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updater/{name}")
	public FileSystemResource getUpdater(@PathVariable String name) throws IOException {
		return new FileSystemResource(appService.getUpdaterForApp(name));
	}
}
