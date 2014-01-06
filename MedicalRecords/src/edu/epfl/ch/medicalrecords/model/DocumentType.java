package edu.epfl.ch.medicalrecords.model;

public enum DocumentType {

	CEDULA_CIUDADANIA("C.C."), 
	CEDULA_EXTRANJERIA("C.E."),
	OTRO("OTRO"),
	PASAPORTE("Pasaporte"), 
	REGISTRO_CIVIL("Registro Civil"),
	TARJETA_IDENTIDAD("T.I."); 
	
	String text;
	
	DocumentType(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
