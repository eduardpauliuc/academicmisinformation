package com.example.security.services;

import com.example.models.StaffMember;

import java.util.List;
import java.util.Optional;

public interface IStaffMemberService {

    List<StaffMember> getAllStaffMembers();

    StaffMember saveStaffMember(StaffMember staffMember);

    Optional<StaffMember> findStaffMemberById(Long id);

    void deleteStaffMemberById(Long id);

}
