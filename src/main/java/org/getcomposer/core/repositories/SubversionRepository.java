package org.getcomposer.core.repositories;

public class SubversionRepository extends VcsRepository implements Cloneable {

	public SubversionRepository() {
		super("svn");
	}

	public String getTrunkPath() {
		return getAsString("trunk-path");
	}
	
	public void setTrunkPath(String path) {
		set("trunk-path", path);
	}
	
	public String getBranchesPath() {
		return getAsString("branches-path");
	}
	
	public void setBranchesPath(String path) {
		set("branches-path", path);
	}
	
	public String getTagsPath() {
		return getAsString("tags-path");
	}
	
	public void setTagsPath(String path) {
		set("tags-path", path);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public SubversionRepository clone() {
		SubversionRepository clone = new SubversionRepository();
		cloneProperties(clone);
		return clone;
	}
}
