package org.getcomposer.internal.serialization;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.Map.Entry;

import org.getcomposer.collection.Dependencies;
import org.getcomposer.entities.Dependency;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DependenciesSerializer implements JsonDeserializer<Dependencies>, JsonSerializer<Dependencies> {

	public JsonElement serialize(Dependencies src, Type typeOfSrc,
			JsonSerializationContext context) {
		
		JsonObject json = new JsonObject();
		for (Dependency dep : src) {
			json.addProperty(dep.getName(), dep.getVersion());
		}
		return json;
	}

	public Dependencies deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		
		Dependencies deps = new Dependencies();
		
		Set<Entry<String, JsonElement>> items = json.getAsJsonObject().entrySet();
		
		for (Entry<String, JsonElement> entry : items) {
			deps.set(entry.getKey(), (new Dependency()).setName(entry.getKey()).setVersion(entry.getValue().getAsString()));
		}
		
		return deps;
	}

}