package de.eins.updateserver.app;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import de.eins.updateserver.common.AbstractTimestampEntity;

@Entity 
public class Version extends AbstractTimestampEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String pathToJar;
	private String versionNumber;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPathToJar() {
		return pathToJar;
	}

	public void setPathToJar(String pathToJar) {
		this.pathToJar = pathToJar;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

}
