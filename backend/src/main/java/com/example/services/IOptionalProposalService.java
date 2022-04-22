package com.example.services;

import com.example.models.OptionalProposal;
import com.example.payload.requests.OptionalProposalDTO;

public interface IOptionalProposalService {

    OptionalProposal saveOptionalProposal(OptionalProposal optionalProposal);

    OptionalProposal convertToOptionalProposal(OptionalProposalDTO optionalProposalDTO);

}
