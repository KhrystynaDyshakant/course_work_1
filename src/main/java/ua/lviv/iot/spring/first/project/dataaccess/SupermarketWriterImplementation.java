package ua.lviv.iot.spring.first.project.dataaccess;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ua.lviv.iot.spring.first.project.rest.model.Supermarket;
import com.opencsv.CSVWriter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.regex.Pattern;

@Component
public class SupermarketWriterImplementation implements SupermarketWriter {
    private final String basePath = System.getProperty("user.dir");
    private final String supermarketsBasePath = String.
            format("%s/data/supermarkets", basePath);
    private final String todayFileName = String.format("supermarket-%s.csv",
            LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
    private final String todayFilePath = String.format("%s/%s",
            supermarketsBasePath, todayFileName);
    private final String[] fields = Arrays.stream(Supermarket.class.
            getDeclaredFields()).map(Field::getName).toArray(String[]::new);
    private final Map<Integer, Supermarket> supermarketsMap = new HashMap<>();
    private final Map<Integer, String> supermarketPathMap = new HashMap<>();

    @PostConstruct
    public final void init() throws IOException {
        List<String> paths = getMonthPaths(supermarketsBasePath,
                LocalDateTime.now());
        for (String path : paths) {
            Map<Integer, Supermarket> supermarkets = loadSupermarkets(path);
            for (Map.Entry<Integer, Supermarket> entry : supermarkets.
                    entrySet()) {
                supermarketsMap.put(entry.getKey(), entry.getValue());
                supermarketPathMap.put(entry.getKey(), path);
            }
        }
    }

    public final List<String> getMonthPaths(final String basePath,
                                            final LocalDateTime date)
            throws IOException {
        String currentMonth = date.format(DateTimeFormatter.
                ofPattern("yyyy-MM"));
        String currentMonthFileName = String.
                format("supermarket-%s-\\d{2}\\.csv", currentMonth);
        Pattern pattern = Pattern.compile(currentMonthFileName);
        return Files
                .list(Paths.get(basePath))
                .map(Path::toString)
                .filter(path -> pattern.matcher(path).find())
                .sorted()
                .toList();
    }

    public final List<Supermarket> findAll() {
        return new ArrayList<>(supermarketsMap.values());
    }

    public final Supermarket findById(final Integer panelId) {
        return supermarketsMap.get(panelId);
    }

    public final Supermarket save(final Supermarket supermarket) {
        supermarketsMap.put(supermarket.getId(), supermarket);
        File f = new File(todayFilePath);
        boolean exists = f.exists();
        try (
                FileWriter fileWriter = new FileWriter(f, true);
                CSVWriter csvWriter = new CSVWriter(fileWriter)
        ) {
            if (!exists) {
                csvWriter.writeNext(fields);
            }
            final int idIndex = 0;
            final int nameIndex = 1;
            final int addressIndex = 2;
            final int panelIdIndex = 3;
            String[] values = new String[]{
                    supermarket.getId().toString(),
                    supermarket.getName(),
                    supermarket.getAddress(),
                    String.valueOf(supermarket.getPanelId())
            };
            csvWriter.writeNext(values);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return supermarket;
    }

    public final void delete(final Supermarket supermarket) {
        supermarketsMap.remove(supermarket.getId());

        String path = supermarketPathMap.get(supermarket.getId());
        Map<Integer, Supermarket> supermarkets = loadSupermarkets(path);
        supermarkets.remove(supermarket.getId());
        writeSupermarkets(path, new ArrayList<>(supermarkets.values()));
    }

    @Override
    public final Supermarket update(final Supermarket supermarket) {
        supermarketsMap.put(supermarket.getId(), supermarket);

        String path = supermarketPathMap.get(supermarket.getId());
        Map<Integer, Supermarket> supermarkets = loadSupermarkets(path);

        supermarkets.put(supermarket.getId(), supermarket);
        writeSupermarkets(path, new ArrayList<>(supermarkets.values()));

        return supermarket;
    }

    public final int getLastId() {
        if (supermarketsMap.isEmpty()) {
            return 0;
        }
        return Collections.max(supermarketsMap.keySet());
    }

    public final Map<Integer, Supermarket> loadSupermarkets(final String path) {
        Map<Integer, Supermarket> supermarkets = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            reader.skip(1);

            String[] values;
            while ((values = reader.readNext()) != null) {
                final int idIndex = 0;
                final int nameIndex = 1;
                final int addressIndex = 2;
                final int panelIdIndex = 3;
                Supermarket supermarket = new Supermarket(
                        Integer.parseInt(values[idIndex]),
                        values[nameIndex],
                        values[addressIndex],
                        Integer.parseInt(values[panelIdIndex])
                );
                supermarkets.put(supermarket.getId(), supermarket);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return supermarkets;
    }

    public final void writeSupermarkets(final String
                                                path,
                                        final List<Supermarket> supermarkets) {
        try (
                FileWriter fileWriter = new FileWriter(path);
                CSVWriter csvWriter = new CSVWriter(fileWriter)
        ) {
            csvWriter.writeNext(fields);
            for (Supermarket supermarket : supermarkets) {
                final int idIndex = 0;
                final int nameIndex = 1;
                final int addressIndex = 2;
                final int panelIdIndex = 3;
                String[] values = new String[]{
                        supermarket.getId().toString(),
                        supermarket.getName(),
                        String.valueOf(supermarket.getAddress()),
                        String.valueOf(supermarket.getPanelId())
                };
                csvWriter.writeNext(values);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

