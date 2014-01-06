package edu.epfl.ch.medicalrecords.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;

public class MedicalRecordsManager implements Serializable {

	private static final long serialVersionUID = 45743735695L;
	private static final String SEPARADOR_1 = "#%";
	private static MedicalRecordsManager instance;
	private static Objectify ofy;
	private static Map<String, DocumentType> documentTypeByName;

	public synchronized static MedicalRecordsManager getInstance() {
		if (instance == null) {
			instance = new MedicalRecordsManager();
		}
		return instance;
	}

	private MedicalRecordsManager() {
		ObjectifyService.register(Doctor.class);
		ObjectifyService.register(Patient.class);
		ObjectifyService.register(Registry.class);

		ofy = ObjectifyService.ofy();
		documentTypeByName = new HashMap<>();
		for (DocumentType type : DocumentType.values()) {
			documentTypeByName.put(type.text, type);
		}
	}

	public Doctor getDoctor(String username) {
		Doctor d = ofy.load().type(Doctor.class).id(username).now();
		return d;
	}

	public static void loadDoctors(ServletContext context)
			throws IOException {
		Doctor newDoctor = new Doctor("arthoviedo");
		List<Patient> patients = new ArrayList<>();

		String path = "/WEB-INF/data/pacientes.txt";
		BufferedReader br;
		if (context == null) { 
			br = new BufferedReader(new FileReader("./war/WEB-INF/data/pacientes.txt"));
		} else {
			InputStream is = context.getResourceAsStream(path);
			br = new BufferedReader(new InputStreamReader(is));
		}
		//Scanner s = new Scanner(is);
		String numberPatientsLine = br.readLine();
		numberPatientsLine = numberPatientsLine.trim();
		System.out.println(Charset.defaultCharset());
		int numeroPacientes = Integer.parseInt(numberPatientsLine);
		
		for (int i = 0; i < numeroPacientes; i++) {
			try {
				String lineaPaciente = br.readLine();
				String[] parts = lineaPaciente.split(SEPARADOR_1);
				// TODO
				DocumentType docType = documentTypeByName.get(parts[3]) != null ? documentTypeByName
						.get(parts[3]) : DocumentType.OTRO;
				Patient p = new Patient();
				p.setFirstName(parts[0]);
				p.setLastName(parts[1]);
				p.setMedicalEntity(parts[2]);
				p.setDocumentType(docType);
				p.setDocumentNumber(parts[4]);
				p.setAge(parts[5]);
				newDoctor.getListPatients().add(Ref.create(p));
				patients.add(p);

				int numeroRegistros = Integer.parseInt(parts[6]);
				for (int j = 0; j < numeroRegistros; j++) {
					String lineaRegistro = br.readLine();
					String[] partes2 = lineaRegistro.split(SEPARADOR_1);

					String dato_fechaRegistro = partes2[0].split(" ")[0];

					String partesFechaRegistro[] = dato_fechaRegistro
							.split("/");
					Calendar fechaRegistro = Calendar.getInstance();
					fechaRegistro.set(Integer.parseInt(partesFechaRegistro[2]),
							Integer.parseInt(partesFechaRegistro[1]),
							Integer.parseInt(partesFechaRegistro[0]));

					String fecha_hora = partes2[0].split(" ")[1];
					String hora = fecha_hora.split(":")[0];
					String minutos = fecha_hora.split(":")[1];

					fechaRegistro.set(Calendar.HOUR_OF_DAY,
							Integer.parseInt(hora));
					fechaRegistro.set(Calendar.MINUTE,
							Integer.parseInt(minutos));

					String text = "";
					if (partes2.length == 2)
						text = partes2[1];
					else
						continue;
					Registry r = new Registry(text,
							Utils.getFechaAsString(fechaRegistro));
					ofy.save().entity(r).now();
					p.getRegistries().add(Ref.create(r));
				}
				ofy.save().entity(p).now();
			} catch (Exception e) {
				System.out.println("No se pudo cargar el paciente #" + i);
			}
		}
		br.close();
		ofy.save().entity(newDoctor).now();
	}
	
	public void savePatient(Patient patient) {
		ofy.save().entity(patient).now();
	}
	
	public static void main(String[] args) throws IOException {
		loadDoctors(null);
	}
}
