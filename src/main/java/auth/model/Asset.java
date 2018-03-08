package auth.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Asset {

	private Long id;	
	private String name;
	private String description;
	private String type;	
	private List<Asset> parents;
	private List<Asset> children;
	
	public Asset(){}
	
	public Asset(long id, String name, String description, String type) {
		
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.parents = new ArrayList<Asset>();
		this.children = new ArrayList<Asset>();		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Asset> getParents() {
		return parents;
	}

	public void setParents(List<Asset> parents) {
		this.parents = parents;
	}

	public List<Asset> getChildren() {
		return children;
	}

	public void setChildren(List<Asset> children) {
		this.children = children;
	}

}
