/*******************************************************************************
 * Copyright (c) 2012 The PDT Extension Group (https://github.com/pdt-eg)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.getcomposer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.getcomposer.serialization.LicenseSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * Represents a composer package. The source can either be a composer.json file
 * or a json response from packagist.org.
 * 
 * See fromJson / fromPackagist for details.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 * 
 */
public class PHPPackage extends IOPackage implements PackageInterface {

	public String name;
	public String type;
	public String description;
	public String homepage;
	public String url;
	public String fullPath;
	public String minimumStability = ComposerConstants.STABILITIES[0];
	public Map<String, String> require;
	
	@SerializedName("require-dev")
	public Map<String, String> requireDev;
	public Autoload autoload;
	
	@SerializedName("target-dir")
	public String targetDir;
	public String version;
	public String versionNormalized;
	public Support support;
	public License license;
	public String[] keywords;
	public Versions versions;
	public List<Person> authors;
	public List<Person> maintainers;

	public PHPPackage() {
		authors = new ArrayList<Person>();
		require = new HashMap<String, String>();
		requireDev = new HashMap<String, String>();
		support = new Support();
		license = new License();
	}

	public String toString() {
		return name;
	}

	/**
	 * Deserializes a package from a composer.json file
	 * 
	 * @param input
	 * @return {@link PHPPackage} the deserialized package
	 * @throws FileNotFoundException
	 */
	public static PHPPackage fromFile(File input) throws FileNotFoundException {
		return fromFile(input, PHPPackage.class);
	}

//	/**
//	 * Deserializes a package from packagist.org, e.g.
//	 * http://packagist.org/packages/react/react.json
//	 * 
//	 * @param input
//	 * @return {@link PHPPackage} the deserialized package
//	 * @throws FileNotFoundException
//	 */
//	public static PHPPackage fromPackagist(File input)
//			throws FileNotFoundException {
//		Gson gson = getBuilder();
//		InputStream stream = new FileInputStream(input);
//		InputStreamReader reader = new InputStreamReader(stream);
//		PackagistPackage packagistPackage = gson.fromJson(reader,
//				PackagistPackage.class);
//
//		return packagistPackage.phpPackage;
//	}
	
	/**
	 * Serializes the package to json
	 * 
	 * @return the serialized json package
	 */
	public String toJson() {
		Gson gson = getOutputBuilder();
		return gson.toJson(this, PHPPackage.class);
	}

	public static Gson getOutputBuilder() {
		return new GsonBuilder()
			.setPrettyPrinting()
			.create();
	}

	/**
	 * Retrieve a Gson with the proper TypeAdapters and FieldNamingStrategy
	 * 
	 * @return {@link Gson}
	 */
	public static Gson getBuilder() {
		return new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(License.class, new LicenseSerializer())
//				.setFieldNamingStrategy(new ComposerFieldNamingStrategy())
				.create();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.getcomposer.core.PackageInterface#getDefaultVersion()
	 */
	public String getDefaultVersion() {
		return versions.getDefaultVersion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.getcomposer.core.PackageInterface#getPackageName(java.lang.String)
	 */
	public String getPackageName(String version) throws Exception {
		if (!versions.has(version)) {
			throw new Exception("Invalid version " + version + " for package "
					+ name);
		}

		return String.format("%s:%s", name, version);
	}
	
	/**
	 * 
	 * Helper class for deserializing a packagist.org json object.
	 * 
	 * @author Robert Gruendler <r.gruendler@gmail.com>
	 * 
	 */
	public class PackagistPackage {

		@SerializedName("package")
		public PHPPackage phpPackage;
		
		public PackagistPackage() {}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.getcomposer.core.PackageInterface#getName()
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.getcomposer.core.PackageInterface#getType()
	 */
	public String getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.getcomposer.core.PackageInterface#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.getcomposer.core.PackageInterface#getHomepage()
	 */
	public String getHomepage() {
		return homepage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.getcomposer.core.PackageInterface#getUrl()
	 */
	public String getUrl() {
		return url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.getcomposer.core.PackageInterface#getFullPath()
	 */
	public String getFullPath() {
		return fullPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.getcomposer.core.PackageInterface#getRequire()
	 */
	public Map<String, String> getRequire() {
		return require;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.getcomposer.core.PackageInterface#getRequireDev()
	 */
	public Map<String, String> getRequireDev() {
		return requireDev;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.getcomposer.core.PackageInterface#getAutoload()
	 */
	public Autoload getAutoload() {
		return autoload;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.getcomposer.core.PackageInterface#getTargetDir()
	 */
	public String getTargetDir() {
		return targetDir;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.getcomposer.core.PackageInterface#getVersion()
	 */
	public String getVersion() {
		return version;
	}

	public String getVersionNormalized() {
		return versionNormalized;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.getcomposer.core.PackageInterface#getLicense()
	 */
	public License getLicense() {
		return license;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.getcomposer.core.PackageInterface#getKeywords()
	 */
	public String[] getKeywords() {
		return keywords;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.getcomposer.core.PackageInterface#getVersions()
	 */
	public Versions getVersions() {
		return versions;
	}

	public String getMinimumStability() {
		return minimumStability;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.getcomposer.core.PackageInterface#getAuthors()
	 */
	public List<Person> getAuthors() {
		return authors;
	}
	
	@SuppressWarnings("unchecked")
	public void addAuthor(Person author) {
		ArrayList<Person> authors = (ArrayList<Person>) ((ArrayList<Person>) this.authors).clone();
		this.authors.add(author);
		firePropertyChange("authors", authors, this.authors);
	}
	
	@SuppressWarnings("unchecked")
	public void removeAuthor(Person author) {
		ArrayList<Person> authors = (ArrayList<Person>) ((ArrayList<Person>) this.authors).clone();
		this.authors.remove(author);
		firePropertyChange("authors", authors, this.authors);
	}
	
	public void setAuthors(List<Person> authors) {
		firePropertyChange("authors", this.authors, this.authors = (ArrayList<Person>) authors); 
	}
	

	public List<Person> getMaintainers() {
		return maintainers;
	}
	
	@SuppressWarnings("unchecked")
	public void addMaintainer(Person maintainer) {
		ArrayList<Person> maintainers = (ArrayList<Person>) ((ArrayList<Person>) this.maintainers).clone();
		this.maintainers.add(maintainer);
		firePropertyChange("maintainers", maintainers, this.maintainers);
	}
	
	@SuppressWarnings("unchecked")
	public void removeMaintainer(Person maintainer) {
		ArrayList<Person> maintainers = (ArrayList<Person>) ((ArrayList<Person>) this.maintainers).clone();
		this.maintainers.remove(maintainer);
		firePropertyChange("maintainers", maintainers, this.maintainers);
	}
	
	public void setMaintainers(List<Person> maintainers) {
		firePropertyChange("maintainers", this.maintainers, this.maintainers = (ArrayList<Person>) maintainers); 
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		firePropertyChange("name", this.name, this.name = name);
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		firePropertyChange("type", this.type, this.type = type);
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		firePropertyChange("description", this.description, this.description = description);
	}

	/**
	 * @param homepage the homepage to set
	 */
	public void setHomepage(String homepage) {
		firePropertyChange("homepage", this.homepage, this.homepage = homepage);
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		firePropertyChange("url", this.url, this.url = url);
	}

	/**
	 * @param minimumStability the minimumStability to set
	 */
	public void setMinimumStability(String minimumStability) {
		firePropertyChange("minimumStability", this.minimumStability, this.minimumStability = minimumStability);
	}

	/**
	 * @param require the require to set
	 */
	public void setRequire(Map<String, String> require) {
		firePropertyChange("require", this.require, this.require = require);
	}
	
	public void addRequire(String phpPackage, String version) {
		Map<String, String> oldRequire = new HashMap <String, String>(require);
		require.put(phpPackage, version);
		firePropertyChange("require", oldRequire, require);
	}
	
	public void removeRequire(String phpPackage) {
		Map<String, String> oldRequire = new HashMap <String, String>(require);
		require.remove(phpPackage);
		firePropertyChange("require", oldRequire, require);
	}

	/**
	 * @param requireDev the requireDev to set
	 */
	public void setRequireDev(Map<String, String> requireDev) {
		firePropertyChange("requireDev", this.requireDev, this.requireDev = requireDev);
	}

	public void addRequireDev(String phpPackage, String version) {
		Map<String, String> oldRequireDev = new HashMap <String, String>(requireDev);
		requireDev.put(phpPackage, version);
		firePropertyChange("requireDev", oldRequireDev, requireDev);
	}

	public void removeRequireDev(String phpPackage) {
		Map<String, String> oldRequireDev = new HashMap <String, String>(requireDev);
		requireDev.remove(phpPackage);
		firePropertyChange("requireDev", oldRequireDev, requireDev);
	}

	/**
	 * @param targetDir the targetDir to set
	 */
	public void setTargetDir(String targetDir) {
		firePropertyChange("targetDir", this.targetDir, this.targetDir = targetDir);
	}

	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String[] keywords) {
		firePropertyChange("keywords", this.keywords, this.keywords = keywords);
	}
	
	/**
	 * @param support the support to set
	 */
	public void setSupport(Support support) {
		firePropertyChange("support", this.support, this.support = support);
	}

	public SupportInterface getSupport() {
		return support;
	}
	
	
}