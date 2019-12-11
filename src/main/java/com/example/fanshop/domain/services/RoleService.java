package com.example.fanshop.domain.services;

import com.example.fanshop.domain.services.models.RoleServiceModel;

import java.util.Set;

public interface RoleService {

    void seedRolesInDB();

    RoleServiceModel findByAuthority(String role);

    Set<RoleServiceModel> findAllRoles();
}
