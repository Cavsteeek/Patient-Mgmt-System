package com.pm.pms.mapper;

import com.pm.pms.dto.PatientResponseDTO;
import com.pm.pms.model.Patient;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient){
        PatientResponseDTO patientDTO = new PatientResponseDTO();
        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patient.getName());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());

        return patientDTO;
    }
}
