package org.nalda.adventofcode2023;

import org.nalda.adventofcode2023.trebuchet.Trebuchet;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public class ResourceUtil {
    public static Path getInputPath(String name) throws URISyntaxException {
        final URL resource = Trebuchet.class.getClassLoader().getResource(name);
        return new File(resource.toURI()).toPath();
    }
}
