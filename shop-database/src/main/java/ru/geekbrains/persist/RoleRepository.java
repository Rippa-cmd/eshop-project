package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.persist.model.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAll();

    List<Role> findByName(String name);

//    default Set<RoleDto> mapToRoleDto() {
//        return findAll().stream()
//                .map(role -> new RoleDto(role.getId(), role.getName()))
//                .collect(Collectors.toSet());
//    }
}
