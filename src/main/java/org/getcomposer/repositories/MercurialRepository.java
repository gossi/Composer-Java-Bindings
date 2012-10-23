package org.getcomposer.repositories;

import java.io.File;
import java.io.FileNotFoundException;

public class MercurialRepository extends VcsRepository {

	public MercurialRepository() {
		super("hg");
	}
	
	public static MercurialRepository fromFile(File input) throws FileNotFoundException {
		return fromFile(input, MercurialRepository.class);
	}
	
	public static MercurialRepository fromJson(String json) throws FileNotFoundException {
		return fromJson(json, MercurialRepository.class);
	}
}
