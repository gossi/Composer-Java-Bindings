package org.getcomposer.core;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.LinkedList;

import org.getcomposer.core.collection.Persons;
import org.getcomposer.core.collection.Versions;
import org.getcomposer.core.repositories.PackageRepository;
import org.getcomposer.packages.PackagistDownloader;
import org.json.simple.JSONObject;

public class RepositoryPackage extends DistributedPackage {
	
	private Versions versions = new Versions();
	private Persons maintainers = new Persons();
	
	public RepositoryPackage() {
		super();
		listen();
	}
	
	public RepositoryPackage(Object json) {
		this();
		fromJson(json);
		listen();
	}
	
	public RepositoryPackage(String json) {
		this();
		fromJson(json);
		listen();
	}
	
	public RepositoryPackage(File file) throws IOException {
		this();
		fromJson(file);
		listen();
	}
	
	public RepositoryPackage(Reader reader) throws IOException {
		this();
		fromJson(reader);
		listen();
	}
	
	protected void parse(Object obj) {
		if (obj instanceof JSONObject) {

			JSONObject json = (JSONObject)obj;

			// parsed from super:
			// repository
			
			parseField(json, "versions");
			parseField(json, "maintainers");
		}
		
		super.parse(obj);
	}
	
	@Override
	public Object prepareJson(LinkedList<String> fields) {
		String[] order = new String[]{"maintainers", "repository", "versions"};
		return super.prepareJson(new LinkedList<String>(Arrays.asList(order)));
	}
	
	/**
	 * Deserializes packages from packagist.org, e.g.
	 * http://packagist.org/packages/react/react.json
	 * 
	 * @param input
	 * @return the deserialized package
	 * @throws IOException 
	 */
	public static RepositoryPackage fromPackageRepository(File input) throws IOException {
		PackageRepository repo = new PackageRepository(input);
		return repo.getPackage();
	}
	
	/**
	 * Deserializes packages from packagist.org, e.g.
	 * http://packagist.org/packages/Symfony/Router.json
	 * 
	 * @param name the package name, such as Symfony/Router
	 * @return the deserialized package
	 * @throws IOException
	 */
	public static RepositoryPackage fromPackagist(String name) throws Exception {
		
		PackagistDownloader downloader = new PackagistDownloader();
		return downloader.loadPackage(name);
	}
	
	
	/**
	 * Returns the versions
	 * 
	 * @return the versions
	 */
	public Versions getVersions() {
		return versions;
	}

	/**
	 *
	 * Returns the package name suitable for passing it to
	 * "composer.phar require"
	 *
	 * @param version
	 * @return String the package/version combination
	 * @throws Exception
	 */
	public String getPackageName(String version) throws Exception {
		if (!this.versions.has(version)) {
			throw new Exception("Invalid version " + version + " for package "
					+ getName());
		}

		return String.format("%s:%s", getName(), version);
	}
	
	/**
	 * Returns the maintainers
	 * 
	 * @return the maintainers
	 */
	public Persons getMaintainers() {
		return maintainers;
	}

	/**
	 * Returns the repository url
	 * 
	 * @return the repository
	 */
	public String getRepository() {
		return getAsString("repository");
	}

	/**
	 * @param repository the repository to set
	 */
	public void setRepository(String repository) {
		set("repository", repository);
	}
}
