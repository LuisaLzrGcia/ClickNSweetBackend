package com.clicknsweet.clicknsweet.service;

import com.clicknsweet.clicknsweet.model.Role;
import com.clicknsweet.clicknsweet.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

    @Service
    public class RoleService {

        private final RoleRepository roleRepository;

        @Autowired
        public RoleService(RoleRepository roleRepository) {
            this.roleRepository = roleRepository;
        }

        public Role findById(Integer id) {
            return roleRepository.findById(id).orElse(null);
        }


    }

