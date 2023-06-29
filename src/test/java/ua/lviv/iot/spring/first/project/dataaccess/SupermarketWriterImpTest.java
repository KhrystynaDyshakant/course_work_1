package ua.lviv.iot.spring.first.project.dataaccess;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
//import org.junit.platform.commons.util.CollectionUtils;
import org.junit.rules.TemporaryFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.lviv.iot.spring.first.project.rest.model.Supermarket;
import org.apache.commons.collections.CollectionUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SpringBootTest(classes = SupermarketWriterImplementation.class)
class SupermarketWriterImpTest {
    @Autowired
    SupermarketWriter supermarketWriter;
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    void testWriteSupermarkets() throws IOException {
        tempFolder.create();
        List<Supermarket> supermarkets = new ArrayList<>();
        supermarkets.add(new Supermarket(1, "Metro", "street Chornovil", 1));
        supermarkets.add(new Supermarket(2, "Atb", "street Franko", 2));
        supermarkets.add(new Supermarket(3, "Bluzko", "street Shevchenko", 3));
        String path = String.format("%s/supermarket_test.csv", tempFolder.getRoot().getPath());
        supermarketWriter.writeSupermarkets(path, supermarkets);
        Collection<Supermarket> loadedSupermarkets = supermarketWriter.loadSupermarkets(path).values();
        assert CollectionUtils.isEqualCollection(supermarkets, loadedSupermarkets);
    }

    @Test
    void testGetMonthPaths() throws IOException {
        tempFolder.create();
        String basePath = tempFolder.getRoot().getPath();
        List<Path> prevMonthPaths = List.of(Paths.get(basePath, "supermarket-2023-05-10.csv"));
        List<Path> currentMonthPaths = Arrays.asList(
                Paths.get(basePath, "supermarket-2023-06-13.csv"),
                Paths.get(basePath, "supermarket-2023-06-14.csv")
        );
        List<Path> nextMonthPaths = List.of(Paths.get(basePath, "supermarket-2023-07-05.csv"));
        List<Path> createPaths = new ArrayList<>();
        createPaths.addAll(prevMonthPaths);
        createPaths.addAll(currentMonthPaths);
        createPaths.addAll(nextMonthPaths);
        for (Path path : createPaths)
            new File(path.toString()).createNewFile();
        LocalDateTime data = LocalDateTime.of(2023, 6, 13, 0, 0);
        List<String> actualPaths = supermarketWriter.getMonthPaths(tempFolder.getRoot().getPath(), data);

        List<String> expectedPaths = currentMonthPaths.stream().map(Path::toString).toList();
        assert CollectionUtils.isEqualCollection(actualPaths, expectedPaths);
    }
}