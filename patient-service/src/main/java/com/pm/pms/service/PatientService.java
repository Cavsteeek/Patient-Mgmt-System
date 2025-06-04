package com.pm.pms.service;

import com.pm.pms.dto.PatientRequestDTO;
import com.pm.pms.dto.PatientResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PatientService {
    List<PatientResponseDTO> getPatients();

    PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO);

    PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO);

    void deletePatient(UUID id);
}
