package com.example.repositories;

import com.example.models.EStatus;
import com.example.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStatusRepository extends JpaRepository<Status, Long> {

    Status findStatusByName(EStatus name);

}
