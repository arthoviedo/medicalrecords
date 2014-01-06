package edu.epfl.ch.medicalrecords.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Patient implements Serializable {

	private static final long serialVersionUID = 87567587L;

	private String medicalEntity;

	private String firstName;

	private String lastName;
	@Id
	private String documentNumber;

	private DocumentType documentType;

	private String age;

	List<Ref<Registry>> registries = new ArrayList<>();
	
	public Patient() {
	}

	public String getMedicalEntity() {
		return medicalEntity;
	}

	public void setMedicalEntity(String medicalEntity) {
		this.medicalEntity = medicalEntity;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public List<Ref<Registry>> getRegistries() {
		return registries;
	}

	public void setRegistries(List<Ref<Registry>> registries) {
		this.registries = registries;
	}
	
	
}
