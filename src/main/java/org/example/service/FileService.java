package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileService {
    public void deleteFileIfExists(Path selected_path, String file_name) {
        try (Stream<Path> walk = Files.walk(selected_path)) {
            Optional<Path> selected_file = walk
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().contains(file_name))
                    .findFirst();
            if (selected_file.isPresent()) {
                Files.delete(selected_file.get());
            } else {
                System.out.println("File not found");
            }
        } catch (IOException e) {
            System.out.println("Error.");
        }
    }
}
