package com.example.services;

import com.example.models.OptionalPreference;
import com.example.models.OptionalProposal;
import com.example.payload.requests.OptionalProposalDTO;

import java.util.Optional;

public interface IOptionalProposalService {

    OptionalProposal saveOptionalProposal(OptionalProposal optionalProposal);

    OptionalProposal convertToOptionalProposal(OptionalProposalDTO optionalProposalDTO);

    Optional<OptionalProposal> findOptionalProposalById(Long id);

    void deleteOptionalProposal(Long id);

}
