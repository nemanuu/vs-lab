package hska.iwi.category;

import javax.persistence.*;

/**
 * This class contains details about categories.
 */
@Entity
public class Category implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
