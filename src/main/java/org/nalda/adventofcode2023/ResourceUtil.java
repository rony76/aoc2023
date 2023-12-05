package org.nalda.adventofcode2023;

import lombok.SneakyThrows;
import org.nalda.adventofcode2023.trebuchet.Trebuchet;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ResourceUtil {
    @SneakyThrows
    private static Path getInputPath(String name) {
        final URL resource = Trebuchet.class.getClassLoader().getResource(name);
        return new File(resource.toURI()).toPath();
    }

    @SneakyThrows
    public static Stream<String> getLineStream(String name) {
        return Files.lines(getInputPath(name));
    }

    @SneakyThrows
    public static Supplier<Stream<String>> getLineStreamSupplier(String name) {
        return () -> {
            try {
                return Files.lines(getInputPath(name));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @SneakyThrows
    public static List<String> getLineList(String name) {
        return Files.readAllLines(getInputPath(name));
    }
}
