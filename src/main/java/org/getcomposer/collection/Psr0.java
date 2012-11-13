package org.getcomposer.collection;

import java.util.Iterator;
import java.util.List;

import org.getcomposer.entities.Dependency;
import org.getcomposer.entities.Namespace;
import org.getcomposer.serialization.Psr0Serializer;

/**
 * Represents a psr-0 entity in a composer package.
 * 
 * @see http://getcomposer.org/doc/04-schema.md#psr-0
 * @author Thomas Gossmann <gos.si>
 */
public class Psr0 extends JsonMap<Psr0, Namespace> implements Iterable<Namespace> {

	public Psr0() {
		super(Dependency.class);
	}
	
	/**
	 * Adds a new dependency.
	 * 
	 * @param dependency the new dependency
	 * @return this
	 */
	public void add(Namespace namespace) {
		super.set(namespace.getNamespace(), namespace);
	}

	/**
	 * Removes a dependency.
	 * 
	 * @param dependency the dependency to remove
	 */
	public void remove(Namespace namespace) {
		super.remove(namespace.getNamespace());
	}
	
	public List<Namespace> getAll() {
		return (List<Namespace>) properties.values();
	}

	public Iterator<Namespace> iterator() {
		return (Iterator<Namespace>)properties.values().iterator();
	}

	public static Object getSerializer() {
		return new Psr0Serializer();
	}
}
