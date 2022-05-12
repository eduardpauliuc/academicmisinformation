package com.example.services;

import com.example.models.EStatus;
import com.example.models.Status;
import com.example.repositories.IStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatusService implements IStatusService {

    private final IStatusRepository statusRepository;

    @Override
    public Status getRejectedStatus() {
        return statusRepository.findStatusByName(EStatus.REJECTED);
    }
}
