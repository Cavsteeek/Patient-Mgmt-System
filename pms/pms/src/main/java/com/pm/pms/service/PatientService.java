package com.pm.pms.service;

import com.pm.pms.dto.PatientResponseDTO;

import java.util.List;

public interface PatientService {
    List<PatientResponseDTO> getPatients();
}
