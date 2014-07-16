package edu.epfl.ch.medicalrecords.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Ref;

import edu.epfl.ch.medicalrecords.model.Doctor;
import edu.epfl.ch.medicalrecords.model.MedicalRecordsManager;
import edu.epfl.ch.medicalrecords.model.Patient;
import edu.epfl.ch.medicalrecords.model.Registry;
import edu.epfl.ch.medicalrecords.utils.Console;

@ManagedBean(name = "medicalRecordsBean")
@SessionScoped
public class MedicalRecordsBean implements Serializable {

	private static final long serialVersionUID = 1346L;
	String message;
	MedicalRecordsManager manager;
	String username;
	Doctor doctor;
	List<Patient> patients = new ArrayList<>();
	List<Registry> selectedPatientsRegistries = new ArrayList<>();
	
	private Patient selectedPatient;
	
	private String firstNameNewPatient;
	private String lastNameNewPatient;
	private String documentNumberNewPatient;
	private String ageNewPatient;
	private String medicalEntityNewPatient;
    
	public MedicalRecordsBean() {
		Console.print("Initializing bean");

		// LoginFilter should already ensure that user is logged in
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		username = user.getNickname();
		manager = MedicalRecordsManager.getInstance();
		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		try {
			manager.loadDoctors(servletContext);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			message += "There was an error while loading the list of patients";
		}
		System.out.println("User:" + username);
		doctor = manager.getDoctor(username);
		selectedPatient = new Patient();
		loadPatients();
		if (doctor == null) {
			message += "There's no record for you!";
		} else {
			message += "You have: " + doctor.getListPatients().size()
					+ " patients";
		}
	}

	public void loadPatients() {
		patients = new ArrayList<>();
		for (Ref<Patient> currentPatient : doctor.getListPatients()) {
			patients.add(currentPatient.get());
		}
	}

	public void updateSelectedPatient(ActionEvent event) {
		manager.savePatient(selectedPatient);
	}
	
	public void createNewPatient(ActionEvent event) {
	    Console.print(lastNameNewPatient);
	    Console.print(firstNameNewPatient);
	    Console.print(documentNumberNewPatient);
	    Console.print(medicalEntityNewPatient);
        Console.print(ageNewPatient);
	    
	}

	public boolean getLogin() {
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		HttpServletResponse res = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		message = "";

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user != null) {
			message = "Hi " + user.getNickname();
			username = user.getNickname();
			return true;
		} else {
			message = "You are not logged in!";
			String redirect = userService.createLoginURL(req.getRequestURI());
			try {
				res.sendRedirect(redirect);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public Patient getSelectedPatient() {
		return selectedPatient;
	}

	public void setSelectedPatient(Patient selectedPatient) {
		this.selectedPatient = selectedPatient;
		selectedPatientsRegistries = new ArrayList<>();
		System.out.println("Loading patients registries");
		for (Ref<Registry> r : selectedPatient.getRegistries()) {
			selectedPatientsRegistries.add(r.get());
		}
		System.out.println("Number of records: " + selectedPatient.getRegistries().size());
		System.out.println("Number of loaded records: " + selectedPatientsRegistries.size());
		
	}

	public List<Registry> getSelectedPatientsRegistries() {
		return selectedPatientsRegistries;
	}

	public void setSelectedPatientsRegistries(
			List<Registry> selectedPatientsRegistries) {
		this.selectedPatientsRegistries = selectedPatientsRegistries;
	}

   public String getDocumentNumberNewPatient() {
        return documentNumberNewPatient;
    }

    public String getFirstNameNewPatient() {
    return firstNameNewPatient;
}

public void setFirstNameNewPatient(String firstNameNewPatient) {
    this.firstNameNewPatient = firstNameNewPatient;
}

public String getLastNameNewPatient() {
    return lastNameNewPatient;
}

public void setLastNameNewPatient(String lastNameNewPatient) {
    this.lastNameNewPatient = lastNameNewPatient;
}

    public void setDocumentNumberNewPatient(String documentNumberNewPatient) {
        this.documentNumberNewPatient = documentNumberNewPatient;
    }

    public String getAgeNewPatient() {
        return ageNewPatient;
    }

    public void setAgeNewPatient(String ageNewPatient) {
        this.ageNewPatient = ageNewPatient;
    }

    public String getMedicalEntityNewPatient() {
        return medicalEntityNewPatient;
    }

    public void setMedicalEntityNewPatient(String medicalEntityNewPatient) {
        this.medicalEntityNewPatient = medicalEntityNewPatient;
    }

}
