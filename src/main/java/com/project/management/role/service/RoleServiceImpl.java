package com.project.management.role.service;

import com.project.management.role.persistence.RoleRepository;
import com.project.management.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

}
