package org.example;

import org.example.entity.*;
import org.example.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * feed some data  in to the database  and play with  mongosh shell  or some UI tool
 */
@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    private final Random random = new Random();

    @Override
    public void run(String... args) {
        // Generate and insert data
        List<Hospital> hospitals = generateHospitals(10);
        hospitalRepository.saveAll(hospitals);

        List<Doctor> doctors = generateDoctors(50, hospitals);
        doctorRepository.saveAll(doctors);

        List<Patient> patients = generatePatients(500, doctors);
        patientRepository.saveAll(patients);

        List<Appointment> appointments = generateAppointments(500, patients, doctors);
        appointmentRepository.saveAll(appointments);

        List<Prescription> prescriptions = generatePrescriptions(500, patients, doctors);
        prescriptionRepository.saveAll(prescriptions);

        System.out.println("Data seeding completed!");
    }

    private List<Hospital> generateHospitals(int count) {
        List<Hospital> hospitals = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Hospital hospital = new Hospital();
            hospital.setName("Hospital " + i);
            hospital.setAddress("Address " + i);
            hospitals.add(hospital);
        }
        return hospitals;
    }

    private List<Doctor> generateDoctors(int count, List<Hospital> hospitals) {
        List<Doctor> doctors = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Doctor doctor = new Doctor();
            doctor.setName("Doctor " + i);
            doctor.setSpecialization("Specialization " + (i % 10));
            doctor.setHospital(hospitals.get(random.nextInt(hospitals.size())));
            doctors.add(doctor);
        }
        return doctors;
    }

    private List<Patient> generatePatients(int count, List<Doctor> doctors) {
        List<Patient> patients = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Patient patient = new Patient();
            patient.setName("Patient " + i);
            patient.setAge(random.nextInt(80) + 1);
            patient.setGender(i % 2 == 0 ? "Male" : "Female");
            patient.setDoctor(doctors.get(random.nextInt(doctors.size())));
            patients.add(patient);
        }
        return patients;
    }

    private List<Appointment> generateAppointments(int count, List<Patient> patients, List<Doctor> doctors) {
        List<Appointment> appointments = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Appointment appointment = new Appointment();
            appointment.setDate(LocalDateTime.now().minusDays(random.nextInt(30)));
            appointment.setReason("Reason " + i);
            appointment.setPatient(patients.get(random.nextInt(patients.size())));
            appointment.setDoctor(doctors.get(random.nextInt(doctors.size())));
            appointments.add(appointment);
        }
        return appointments;
    }

    private List<Prescription> generatePrescriptions(int count, List<Patient> patients, List<Doctor> doctors) {
        List<Prescription> prescriptions = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Prescription prescription = new Prescription();
            prescription.setMedication("Medication " + i);
            prescription.setDosage("Dosage " + (i % 5 + 1));
            prescription.setInstructions("Take once daily");
            prescription.setPatient(patients.get(random.nextInt(patients.size())));
            prescription.setDoctor(doctors.get(random.nextInt(doctors.size())));
            prescriptions.add(prescription);
        }
        return prescriptions;
    }
}