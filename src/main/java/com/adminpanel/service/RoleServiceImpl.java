package com.adminpanel.service;

import com.adminpanel.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private  RoleRepository roleRepository;


    @Transactional
    public void updateRoleNameById(Long roleId, String newName) {
        roleRepository.updateRoleNameById(roleId, newName);
    }
}
