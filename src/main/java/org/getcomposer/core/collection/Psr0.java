package org.getcomposer.core.collection;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.getcomposer.core.entities.AbstractJsonObject;
import org.getcomposer.core.objects.Namespace;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Represents a psr-0 entity in a composer package.
 * 
 * @see http://getcomposer.org/doc/04-schema.md#psr-0
 * @author Thomas Gossmann <gos.si>
 */
public class Psr0 extends AbstractJsonObject<Namespace> implements Iterable<Namespace> {

	private transient PropertyChangeListener listener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
		}
	};
	
	public Psr0() {
	}
	
	@SuppressWarnings("unchecked")
	protected void parse(Object obj) {
		clear();
		if (obj instanceof JSONObject) {
			JSONObject json = (JSONObject) obj;
			
			for (Entry<String, Object> entry : ((Map<String, Object>)json).entrySet()) {
				Namespace nmspc = new Namespace();
				nmspc.setNamespace(entry.getKey());
				
				if (entry.getValue() instanceof JSONArray) {
					for (Object path : (JSONArray)entry.getValue()) {
						nmspc.add((String)path);
					}
				} else {
					nmspc.add((String)entry.getValue());
				}
				add(nmspc);
			}
		}
	}
	
	@Override
	public Object prepareJson(LinkedList<String> fields) {
		LinkedHashMap<String, Object> out = new LinkedHashMap<String, Object>();
		for (Namespace nmspc : this) {
			Object value = "";
			
			if (nmspc.size() > 1) {
				value = prepareJsonValue(nmspc.getAll());
			} else if (nmspc.size() == 1) {
				value = nmspc.getFirst();
			}
			
			out.put(nmspc.getNamespace(), value);
		}
			
		return out;
	}
	
	/**
	 * Adds a new dependency.
	 * 
	 * @param dependency the new dependency
	 * @return this
	 */
	public void add(Namespace namespace) {
		namespace.addPropertyChangeListener(listener);
		super.set(namespace.getNamespace(), namespace);
	}

	/**
	 * Removes a dependency.
	 * 
	 * @param dependency the dependency to remove
	 */
	public void remove(Namespace namespace) {
		namespace.removePropertyChangeListener(listener);
		super.remove(namespace.getNamespace());
	}
	
	public List<Namespace> getAll() {
		return (List<Namespace>) properties.values();
	}

	public Iterator<Namespace> iterator() {
		return (Iterator<Namespace>)properties.values().iterator();
	}
}
