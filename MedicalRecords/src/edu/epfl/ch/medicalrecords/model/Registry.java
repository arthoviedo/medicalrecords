package edu.epfl.ch.medicalrecords.model;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Registry implements Serializable {
	private static final long serialVersionUID = 43864576L;
	@Id private Long id;
	private String registro;
	private String fechaRegistro;
	
	public Registry(){}
	
	public Registry(String registroP, String fechaP) {
		registro=registroP;fechaRegistro=fechaP;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setRegistro(String registro) {
		this.registro = registro;
	}
	public String getRegistro() {
		return registro;
	}
	
	public String toString(){
		return fechaRegistro;
	}
	
	/**
		
	}*/
}
