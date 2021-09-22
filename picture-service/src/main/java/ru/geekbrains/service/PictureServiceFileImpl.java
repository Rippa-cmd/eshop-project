package ru.geekbrains.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.model.Picture;
import ru.geekbrains.persist.PictureRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
public class PictureServiceFileImpl implements PictureService {

    @Value("${picture.storage.path}")
    private String storagePath;

    private static final Logger logger = LoggerFactory.getLogger(PictureServiceFileImpl.class);

    private final PictureRepository pictureRepository;

    @Autowired
    public PictureServiceFileImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Optional<String> getPictureContentTypeById(long id) {
        return pictureRepository.findById(id)
                .map(Picture::getContentType);
    }

    @Override
    public Optional<byte[]> getPictureDataById(long id) {
        return pictureRepository.findById(id)
                .map(pic -> Path.of(storagePath, pic.getStorageUUID()))
                .filter(Files::exists)
                .map(path -> {
                    try {
                        return Files.readAllBytes(path);
                    } catch (IOException e) {
                        logger.error("Can't read file for picture with id " + id, e);
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public String createPicture(byte[] picture) {
        String fileName = UUID.randomUUID().toString();
        try (OutputStream os = Files.newOutputStream(Path.of(storagePath, fileName))) {
            os.write(picture);
        } catch (IOException e) {
            logger.error("Can't write file", e);
            throw new RuntimeException(e);
        }
        return fileName;
    }

    @Override
    public void deleteById(Long id) {
        /*Delete from local storage*/
        pictureRepository.findById(id)
                .map(pic -> Path.of(storagePath, pic.getStorageUUID()))
                .filter(Files::exists)
                .map(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        logger.error("Can't delete file in path" + path, e);
                    }
                    return null;
                });
        /*Delete record from DB*/
        pictureRepository.deleteById(id);
    }
}
