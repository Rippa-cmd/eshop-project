package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAll();

    List<Role> findByName(String name);

//    default Set<RoleDto> mapToRoleDto() {
//        return findAll().stream()
//                .map(role -> new RoleDto(role.getId(), role.getName()))
//                .collect(Collectors.toSet());
//    }
}
