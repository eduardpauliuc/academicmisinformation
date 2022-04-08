package com.example.repositories;

import com.example.models.StaffMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStaffMemberRepository extends JpaRepository<StaffMember, Long> {
}
