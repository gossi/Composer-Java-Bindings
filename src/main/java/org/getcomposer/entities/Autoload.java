/*******************************************************************************
 * Copyright (c) 2012 The PDT Extension Group (https://github.com/pdt-eg)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.getcomposer.entities;

import org.getcomposer.collection.GenericArray;
import org.getcomposer.collection.Psr0;
import org.getcomposer.internal.serialization.ClientEntitySerializer;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the autoload entity of a composer package.
 * 
 * @see http://getcomposer.org/doc/04-schema.md#autoload
 * @author Robert Gruendler <r.gruendler@gmail.com>
 * @author Thomas Gossmann <gos.si>
 */
public class Autoload extends GenericEntity {
	
	private GenericArray classmap;
	private GenericArray files;
	
	@SerializedName("psr-0")
	private Psr0 psr0;
	
	public String getPsr0Path() {
		if (psr0 == null) {
			return null;
		}
		if (psr0.size() > 0) {
			return ((Namespace)psr0.iterator().next()).get();
		}
		return null;
	}

	public Psr0 getPsr0() {
		return psr0;
	}

	public Autoload setPsr0(Psr0 psr0) {
		firePropertyChange("psr-0", this.psr0, this.psr0 = psr0);
		return this;
	}
	
	public GenericArray getClassmap() {
		return classmap;
	}
	
	public GenericArray getFiles() {
		return files;
	}
	
	
	public static Object getSerializer() {
		return new ClientEntitySerializer<Autoload>();
	}
}