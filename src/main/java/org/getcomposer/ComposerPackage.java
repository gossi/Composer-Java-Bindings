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

import org.getcomposer.collection.Dependencies;
import org.getcomposer.collection.License;
import org.getcomposer.collection.Persons;
import org.getcomposer.collection.Repositories;
import org.getcomposer.entities.Autoload;
import org.getcomposer.entities.Config;
import org.getcomposer.entities.Dependency;
import org.getcomposer.entities.Extra;
import org.getcomposer.entities.Person;
import org.getcomposer.entities.Support;
import org.getcomposer.internal.serialization.ClientEntitySerializer;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Represents a composer package. The source can either be a composer.json file
 * or a json response from packagist.org.
 * 
 * See {@link #fromJson} / {@link #fromPackagist} for details.
 * 
 * @see http://getcomposer.org
 * @author Robert Gruendler <r.gruendler@gmail.com>
 * @author Thomas Gossmann <gos.si>
 * 
 */
public class ComposerPackage extends AbstractPackage {

	private Dependencies require = new Dependencies();
	private Repositories repositories = new Repositories();

	@SerializedName("require-dev")
	private Dependencies requireDev = new Dependencies();
	private Autoload autoload;

	private Support support = new Support();
	private License license = new License();
	private String[] keywords;
	
	private Persons authors = new Persons();
	
	private Extra extra = new Extra();
	private Config config = new Config();
	

	public ComposerPackage() {
		super();
	}


	public String toString() {
		return getName();
	}

	/**
	 * Deserializes a package from a composer.json file
	 * 
	 * @param input
	 * @return the deserialized package
	 * @throws FileNotFoundException
	 */
	public static ComposerPackage fromFile(File input) throws FileNotFoundException {
		return fromFile(input, ComposerPackage.class);
	}
	
	/**
	 * Deserializes a package from a string
	 * 
	 * @param input
	 * @return the deserialized package
	 */
	public static ComposerPackage fromJson(String json) {
		return fromJson(json, ComposerPackage.class);
	}


	
	/**
	 * Serializes the package to json
	 * 
	 * @return the serialized json package
	 */
	public String toJson() {
		Gson gson = getBuilder();
		return gson.toJson(this, ComposerPackage.class);
	}

	/**
	 * Returns the homepage
	 * 
	 * @return the homepage
	 */
	public String getHomepage() {
		return getAsString("homepage");
	}

	/**
	 * Sets the homepage
	 * 
	 * @param homepage the homepage to set
	 * @return this
	 */
	public ComposerPackage setHomepage(String homepage) {
		set("homepage", homepage);
		return this;
	}

	/**
	 * Returns the require dependencies
	 * 
	 * @return
	 */
	public Dependencies getRequire() {
		return require;
	}
	
	/**
	 * Adds a dependency to the require section
	 * 
	 * @param dependency
	 * @return this
	 */
	public ComposerPackage addRequire(Dependency dependency) {
		require.add(dependency);
		return this;
	}
	
	/**
	 * Removes a dependency from the require section
	 * 
	 * @param dependency
	 * @return this
	 */
	public ComposerPackage removeRequire(Dependency dependency) {
		require.remove(dependency);
		return this;
	}

	/**
	 * Returns the require-dev dependencies
	 * @return
	 */
	public Dependencies getRequireDev() {
		return requireDev;
	}
	
	/**
	 * Adds a dependency to the require-dev section
	 * 
	 * @param dependency
	 * @return this
	 */
	public ComposerPackage addRequireDev(Dependency dependency) {
		requireDev.add(dependency);
		return this;
	}
	
	/**
	 * Removes a dependency from the require-dev section
	 * 
	 * @param dependency
	 * @return this
	 */
	public ComposerPackage removeRequireDev(Dependency dependency) {
		requireDev.remove(dependency);
		return this;
	}


	/**
	 * Returns the autoload section
	 * 
	 * @return the autoload section
	 */
	public Autoload getAutoload() {
		return autoload;
	}

	/**
	 * Returns the target-dir
	 * 
	 * @return the target-dir
	 */
	public String getTargetDir() {
		return getAsString("target-dir");
	}
	
	/**
	 * Sets the target-dir
	 * 
	 * @param targetDir the target-dir to set
	 * @return this
	 */
	public ComposerPackage setTargetDir(String targetDir) {
		set("target-dir", targetDir);
		return this;
	}

	/**
	 * Returns the version
	 * 
	 * @return the version
	 */
	public String getVersion() {
		return getAsString("version");
	}
	
	/**
	 * Sets the version
	 * 
	 * @return this
	 */
	public ComposerPackage setVersion(String version) {
		set("version", version);
		return this;
	}
	

	
	/**
	 * Returns the normalized version
	 * 
	 * @return the normalized version
	 */
	public String getVersionNormalized() {
		return getAsString("version_normalized");
	}

	/**
	 * Returns the license section
	 * 
	 * @return the license section
	 */
	public License getLicense() {
		return license;
	}

	/**
	 * Returns the keywords 
	 *
	 * @return the keywords
	 */
	public String[] getKeywords() {
		return keywords;
	}
	
	
	/**
	 * Sets the keywords
	 * 
	 * @param keywords the keywords to set
	 * @return this
	 */
	public ComposerPackage setKeywords(String[] keywords) {
		firePropertyChange("keywords", this.keywords, this.keywords = keywords);
		return this;
	}


	/**
	 * Returns the minimum-stability property
	 * 
	 * @return the minimum-stability
	 */
	public String getMinimumStability() {
		String stabi = getAsString("minimum-stability");
		if (stabi == null) {
			return ComposerConstants.STABILITIES[0];
		} else {
			return stabi;
		}
	}
	

	/**
	 * Sets the minimum-stability property
	 * 
	 * @param minimumStability the minimum-stability to set
	 * @return this
	 */
	public ComposerPackage setMinimumStability(String minimumStability) {
		set("minimum-stability", minimumStability);
		return this;
	}
	
	/**
	 * Returns the authors
	 * 
	 * @return the authors
	 */
	public Persons getAuthors() {
		return authors;
	}
	
	/**
	 * Adds an author
	 * 
	 * @param author
	 * @return this
	 */
	public ComposerPackage addAuthor(Person author) {
		authors.add(author);
		return this;
	}
	
	/**
	 * Removes an author
	 * 
	 * @param author
	 * @return this
	 */
	public ComposerPackage removeAuthor(Person author) {
		authors.remove(author);
		return this;
	}

	/**
	 * Sets the authors
	 * 
	 * @param authors
	 * @return this
	 */
	public ComposerPackage setAuthors(Persons authors) {
		firePropertyChange("authors", this.authors, this.authors = authors);
		return this;
	}

	

	/**
	 * Sets the support section
	 * 
	 * @param support the support to set
	 * @return this
	 */
	public ComposerPackage setSupport(Support support) {
		firePropertyChange("support", this.support, this.support = support);
		return this;
	}

	/**
	 * Returns the support section
	 * 
	 * @return the support section
	 */
	public Support getSupport() {
		return support;
	}

	/**
	 * @return the repositories
	 */
	public Repositories getRepositories() {
		return repositories;
	}

	/**
	 * Sets the repository collection
	 * 
	 * @param repositories the repositories to set
	 * @return this
	 */
	public ComposerPackage setRepositories(Repositories repositories) {
		this.repositories = repositories;
		return this;
	}

	/**
	 * Returns the extra entity
	 * 
	 * @return the extra
	 */
	public Extra getExtra() {
		return extra;
	}
	
	/**
	 * Returns the config entity
	 * 
	 * @return the config
	 */
	public Config getConfig() {
		return config;
	}
	
	public static Object getSerializer() {
		return new ClientEntitySerializer<ComposerPackage>();
	}
}