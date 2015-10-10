package de.eins.updateserver.app;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import de.eins.updateserver.common.AbstractTimestampEntity;

/**
 * An App for which updates can be shared
 */
@Entity
public class App extends AbstractTimestampEntity {

	/**
	 * Internal database-id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Unique Tool-ID
	 */
	@Column(unique = true, nullable = false)
	private String name;

	@OneToMany
	List<Version> versions = new ArrayList<Version>();

	private String updaterFilePath;
	private String imagePath;

	@Column(columnDefinition = "text")
	private String comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Version> getVersions() {
		return versions;
	}

	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}

	public String getUpdaterFilePath() {
		return updaterFilePath;
	}

	public void setUpdaterFilePath(String updaterFilePath) {
		this.updaterFilePath = updaterFilePath;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
