package org.getcomposer.serialization;

import java.lang.reflect.Type;
import java.util.Map.Entry;
import java.util.Set;

import org.getcomposer.PHPPackage;
import org.getcomposer.PackageInterface;
import org.getcomposer.Versions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class VersionsSerializer implements JsonDeserializer<Versions> {

	public Versions deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		
		Versions versions = new Versions();
		
		Set<Entry<String, JsonElement>> jsonVersions = json.getAsJsonObject().entrySet();
		
		for (Entry<String, JsonElement> entry : jsonVersions) {
			versions.add(entry.getKey(), (PackageInterface) context.deserialize(entry.getValue(), PHPPackage.class));
		}
//		versions.add(json., phpPackage)
		
		return versions;
	}

}