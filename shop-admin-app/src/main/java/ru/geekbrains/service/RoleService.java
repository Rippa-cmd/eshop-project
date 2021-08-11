package ru.geekbrains.service;

import ru.geekbrains.controller.RoleDto;

import java.util.Set;

public interface RoleService {
    Set<RoleDto> findAll();

    Set<RoleDto> findByName(String name);
}
