package hska.iwi.eShopMaster.model.businessLogic.dataobjects;

import java.util.HashSet;
import java.util.Set;

/**
 * This class contains details about categories.
 */

public class Category {

	private int id;

	private String name;

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Category setName(String name) {
		this.name = name;
		return this;
	}

}
