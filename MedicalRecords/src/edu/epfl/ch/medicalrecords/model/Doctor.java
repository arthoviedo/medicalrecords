package edu.epfl.ch.medicalrecords.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Doctor implements Serializable {
	private static final long serialVersionUID = 123554465654L;
	@Id
	String username;
	List<Ref<Patient>> listPatients;

	public Doctor () {}
	
	public Doctor(String username) {
		this.username = username;
		listPatients = new ArrayList<>();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Ref<Patient>> getListPatients() {
		return listPatients;
	}

	public void setListPatients(List<Ref<Patient>> listPatients) {
		this.listPatients = listPatients;
	}

}
