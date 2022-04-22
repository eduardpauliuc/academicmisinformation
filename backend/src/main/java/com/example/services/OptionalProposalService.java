package com.example.services;

import com.example.models.EStatus;
import com.example.models.OptionalProposal;
import com.example.payload.requests.OptionalProposalDTO;
import com.example.repositories.IOptionalProposalRepository;
import com.example.repositories.ISpecializationRepository;
import com.example.repositories.IStatusRepository;
import com.example.repositories.ITeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OptionalProposalService implements IOptionalProposalService {
    private IOptionalProposalRepository optionalProposalRepository;
    private IStatusRepository statusRepository;
    private ISpecializationRepository specializationRepository;
    private ITeacherRepository teacherRepository;

    @Override
    public OptionalProposal saveOptionalProposal(OptionalProposal optionalProposal) {
        return this.optionalProposalRepository.save(optionalProposal);
    }

    @Override
    public OptionalProposal convertToOptionalProposal(OptionalProposalDTO optionalProposalDTO) {
        OptionalProposal optionalProposal = new OptionalProposal(
                null,
                this.specializationRepository.getById(optionalProposalDTO.getSpecializationId()),
                this.teacherRepository.getById(optionalProposalDTO.getTeacherId()),
                this.statusRepository.findStatusByName(EStatus.PENDING),
                null,
                optionalProposalDTO.getMaximumStudentsNumber(),
                optionalProposalDTO.getCredits(),
                optionalProposalDTO.getDescription(),
                optionalProposalDTO.getSemesterNumber(),
                optionalProposalDTO.getName()
        );

        return this.optionalProposalRepository.save(optionalProposal);
    }
}
