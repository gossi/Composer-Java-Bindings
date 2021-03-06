package org.getcomposer.core.objects;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

import org.getcomposer.core.collection.JsonArray;

/**
 * Represents a namespace entry in the psr0 entity of a composer package.
 * 
 * @see http://getcomposer.org/doc/04-schema.md#psr-0
 * @author Thomas Gossmann <gos.si>
 *
 */
public class Namespace extends JsonObject {

	private transient JsonArray paths = new JsonArray();
	
	public Namespace() {
		listen();
		paths.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				firePropertyChange(getNamespace() +"."+ evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
			}
		});
	}
	
	@Override
	public String prepareJson(LinkedList<String> fields) {
		return paths.toJson();
	}
	
	/**
	 * Returns the name.
	 * 
	 * @return the name
	 */
	public String getNamespace() {
		return getAsString("namespace");
	}
	
	/**
	 * Sets the name.
	 * 
	 * @param name the name to set
	 */
	public void setNamespace(String namespace) {
		set("namespace", namespace);
	}
	
	public void add(String path) {
		paths.add(path);
	}
	
	/**
	 * Returns the path and if there are more than one, returns the first one.
	 * 
	 * @return the version
	 */
	public String getFirst() {
		return (String) paths.get(0);
	}
	
	public void remove(String path) {
		paths.remove(path);
	}
	
	public JsonArray getAll() {
		return paths;
	}
	
	public int size() {
		return paths.size();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Namespace clone() {
		Namespace clone = new Namespace();
		cloneProperties(clone);
		return clone;
	}
}
