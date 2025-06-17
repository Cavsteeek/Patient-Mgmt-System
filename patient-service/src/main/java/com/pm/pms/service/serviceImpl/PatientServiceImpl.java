package com.pm.pms.service.serviceImpl;

import com.pm.pms.dto.PatientRequestDTO;
import com.pm.pms.dto.PatientResponseDTO;
import com.pm.pms.exception.EmailAlreadyExistsException;
import com.pm.pms.exception.PatientNotFoundException;
import com.pm.pms.grpc.BillingServiceGrpcClient;
import com.pm.pms.mapper.PatientMapper;
import com.pm.pms.model.Patient;
import com.pm.pms.repository.PatientRepository;
import com.pm.pms.service.PatientService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;

    @Override
    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        return patients.stream()
                .map(PatientMapper::toDTO)
                .toList();
    }

    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email" + " already exists " + patientRequestDTO.getEmail());
        }
        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        billingServiceGrpcClient.createBillingAccount(
                newPatient.getId().toString(),
                newPatient.getName(),
                newPatient.getEmail()
        );

        return PatientMapper.toDTO(newPatient);
    }

    @Override
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));
        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A patient with this email" + " already exists " + patientRequestDTO.getEmail());
        }
        if (patientRequestDTO.getName() != null) patient.setName(patientRequestDTO.getName());
        if (patientRequestDTO.getAddress() != null) patient.setAddress(patientRequestDTO.getAddress());
        if (patientRequestDTO.getEmail() != null) patient.setEmail(patientRequestDTO.getEmail());
        if (patientRequestDTO.getDateOfBirth() != null)
            patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }

    @Override
    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }

}

