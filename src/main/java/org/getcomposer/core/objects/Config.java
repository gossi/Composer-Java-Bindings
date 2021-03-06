package org.getcomposer.core.objects;

import org.getcomposer.core.collection.JsonArray;

/**
 * Represents a config entity in a composer package
 * 
 * @see http://getcomposer.org/doc/04-schema.md#config
 * @author Thomas Gossmann <gos.si>
 *
 */
public class Config extends JsonObject {

	/**
	 * Returns the <code>vendor-bin</code> property.
	 * 
	 * @return the <code>vendor-bin</code> property
	 */
	public String getVendorDir() {
		return getAsString("vendor-dir");
	}
	
	/**
	 * Sets the <code>vendor-bin</code> property.
	 * 
	 * @param vendorDir the new <code>vendor-bin</code> value
	 */
	public void setVendorDir(String vendorDir) {
		set("vendor-dir", vendorDir);
	}
	
	/**
	 * Returns the <code>bin-dir</code> property.
	 * 
	 * @return the <code>bin-dir</code> property
	 */
	public String getBinDir() {
		return getAsString("bin-dir");
	}
	
	/**
	 * Sets the <code>bin-dir</code> property.
	 * 
	 * @param binDir the new <code>bin-dir</code> value
	 */
	public void setBinDir(String binDir) {
		set("bin-dir", binDir);
	}
	
	/**
	 * Returns the <code>process-timeout</code> property.
	 * 
	 * @return the <code>process-timeout</code> property
	 */
	public Integer getProcessTimeout() {
		return getAsInteger("process-timeout");
	}
	
	/**
	 * Sets the <code>process-timeout</code> property.
	 * 
	 * @param processTimeout the new <code>process-timeout</code> value
	 */
	public void setProcessTimeout(int processTimeout) {
		set("process-timeout", processTimeout);
	}
	
	/**
	 * Returns the <code>github-protocols</code> property. If this property
	 * isn't present in the json, the default value 
	 * <code>["git", "https", "http"]</code> is returned.
	 * 
	 * @return the <code>github-protocols</code> property
	 */
	public JsonArray getGithubProtocols() {
		JsonArray protocols = getAsArray("github-protocols");
		if (protocols == null) {
			protocols = new JsonArray();
			protocols.add("git");
			protocols.add("https");
			protocols.add("http");
		}
		return protocols;
	}
	
	/**
	 * Sets the <code>github-protocols</code> property.
	 * 
	 * @param githubProtocols the new <code>github-protocols</code> value
	 */
	public void setGithubProtocols(JsonArray githubProtocols) {
		set("github-protocols", githubProtocols);
	}
	
	/**
	 * Sets the <code>notify-on-install</code> property. If this property
	 * isn't present in the json, the default value <code>true</code>
	 * is returned. 
	 * 
	 * @return the <code>notify-on-install</code> property
	 */
	public boolean getNotifyOnInstall() {
		if (has("notify-on-install")) {
			return getAsBoolean("notify-on-install");
		} else {
			return true;
		}
	}
	
	/**
	 * Set the <code>notify-on-install</code> property.
	 * 
	 * @param notifyOnInstall the new <code>notify-on-install</code> value
	 */
	public void setNotifyOnInstall(boolean notifyOnInstall) {
		set("notify-on-install", notifyOnInstall);
	}
}
