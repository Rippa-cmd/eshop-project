package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.RoleDto;
import ru.geekbrains.persist.Role;
import ru.geekbrains.persist.RoleRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<RoleDto> findAll() {
        return mapToRoleDto(roleRepository.findAll());
    }

    @Override
    public Set<RoleDto> findByName(String name) {
        return mapToRoleDto(roleRepository.findByName(name));
    }

    public Set<RoleDto> mapToRoleDto(List<Role> roles) {
        return roles.stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toSet());
    }
}
