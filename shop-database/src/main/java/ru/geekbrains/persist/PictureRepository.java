package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.persist.model.Picture;

import java.util.Optional;

public interface PictureRepository extends JpaRepository<Picture, Long> {

    Optional<Picture> findById(Long id);
}
