package com.example.security.services;

import com.example.models.StaffMember;
import com.example.repositories.IStaffMemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StaffMemberService implements IStaffMemberService{

    private final IStaffMemberRepository staffMemberRepository;

    @Override
    public List<StaffMember> getAllStaffMembers() {
        return staffMemberRepository.findAll();
    }

    @Override
    public StaffMember saveStaffMember(StaffMember staffMember) {
        return staffMemberRepository.save(staffMember);
    }

    @Override
    public Optional<StaffMember> findStaffMemberById(Long id) {
        return staffMemberRepository.findById(id);
    }

    @Override
    public void deleteStaffMemberById(Long id) {
        staffMemberRepository.deleteById(id);
    }
}
