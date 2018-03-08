package auth.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.netflix.ribbon.proxy.annotation.Http.HttpMethod;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resource {
	
	private Long id;
	private String name;
	private String service;
	private String endpoint;
	private HttpMethod method;
	private String description;

	@JsonIgnore
	private Set<User> users;

	public Resource(){}

	public Resource(Long id, String name, String description) {

		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Resource(Long id, String name, String service, String endpoint, HttpMethod method, String description) {
		super();
		this.id = id;
		this.name = name;
		this.service = service;
		this.endpoint = endpoint;
		this.method = method;
		this.description = description;
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

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
