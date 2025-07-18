package com.pm.pms.mapper;

import com.pm.pms.dto.PatientRequestDTO;
import com.pm.pms.dto.PatientResponseDTO;
import com.pm.pms.model.Patient;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient) {
        PatientResponseDTO patientDTO = new PatientResponseDTO();
        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patient.getName());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());

        return patientDTO;
    }

    public static Patient toModel(PatientRequestDTO patientRequestDTO) {
        Patient newPatient = new Patient();
        newPatient.setName(patientRequestDTO.getName());
        newPatient.setEmail(patientRequestDTO.getEmail());
        newPatient.setAddress(patientRequestDTO.getAddress());
        newPatient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        newPatient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));

        return newPatient;
    }
}
