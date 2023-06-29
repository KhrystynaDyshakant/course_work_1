package ua.lviv.iot.spring.first.project.dataaccess;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ua.lviv.iot.spring.first.project.rest.model.Panel;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

@Component
public class PanelWriterImplementation implements PanelWriter {
    private final String basePath = System.getProperty("user.dir");
    private final String panelsBasePath = String.format("%s/data/panels",
            basePath);
    private final String todayFileName = String.format("panel-%s.csv",
            LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
    private final String todayFilePath = String.format("%s/%s", panelsBasePath,
            todayFileName);
    private final String[] fields = Arrays.stream(Panel.class.
            getDeclaredFields()).map(Field::getName).toArray(String[]::new);
    private final Map<Integer, Panel> panelsMap = new HashMap<>();
    private final Map<Integer, String> panelPathMap = new HashMap<>();

    @PostConstruct
    public final void init() throws IOException {
        List<String> paths = getMonthPaths(panelsBasePath, LocalDateTime.now());
        for (String path : paths) {
            Map<Integer, Panel> panels = loadPanels(path);
            for (Map.Entry<Integer, Panel> entry : panels.entrySet()) {
                panelsMap.put(entry.getKey(), entry.getValue());
                panelPathMap.put(entry.getKey(), path);
            }
        }
    }

    public final List<String> getMonthPaths(final String basePath,
                                            final LocalDateTime date)
            throws IOException {
        String currentMonth = date.format(DateTimeFormatter.
                ofPattern("yyyy-MM"));
        String currentMonthFileName = String.format("panel-%s-\\d{2}\\.csv",
                currentMonth);
        Pattern pattern = Pattern.compile(currentMonthFileName);
        return Files
                .list(Paths.get(basePath))
                .map(Path::toString)
                .filter(path -> pattern.matcher(path).find())
                .sorted()
                .toList();
    }

    public final List<Panel> findAll() {
        return new ArrayList<>(panelsMap.values());
    }

    public final Panel findById(final Integer supermarketId) {
        return panelsMap.get(supermarketId);
    }

    public final Panel save(final Panel panel) {
        panelsMap.put(panel.getId(), panel);
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
            final int quantityIndex = 2;
            final int departmentIndex = 3;
            final int manufacturerIndex = 4;
            final int technicalSpecsIndex = 5;
            final int supermarketIdIndex = 6;
            String[] values = new String[]{
                    panel.getId().toString(),
                    panel.getName(),
                    String.valueOf(panel.getQuantity()),
                    panel.getDepartment(),
                    panel.getManufacturer(),
                    panel.getTechnicalSpecifications(),
                    String.valueOf(panel.getSupermarketId())
            };
            csvWriter.writeNext(values);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return panel;
    }

    public final void delete(final Panel panel) {
        panelsMap.remove(panel.getId());

        String path = panelPathMap.get(panel.getId());
        Map<Integer, Panel> panels = loadPanels(path);
        panels.remove(panel.getId());
        writePanels(path, new ArrayList<>(panels.values()));
    }

    @Override
    public final Panel update(final Panel panel) {
        panelsMap.put(panel.getId(), panel);
        String path = panelPathMap.get(panel.getId());
        Map<Integer, Panel> panels = loadPanels(path);
        panels.put(panel.getId(), panel);
        writePanels(path, new ArrayList<>(panels.values()));
        return panel;
    }

    public final int getLastId() {
        if (panelsMap.isEmpty()) {
            return 0;
        }
        return Collections.max(panelsMap.keySet());
    }

    public final Map<Integer, Panel> loadPanels(final String path) {
        Map<Integer, Panel> panels = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            reader.skip(1);

            String[] values;
            while ((values = reader.readNext()) != null) {
                final int idIndex = 0;
                final int nameIndex = 1;
                final int quantityIndex = 2;
                final int departmentIndex = 3;
                final int manufacturerIndex = 4;
                final int technicalSpecsIndex = 5;
                final int supermarketIdIndex = 6;
                Panel panel = new Panel(
                        Integer.parseInt(values[idIndex]),
                        values[nameIndex],
                        Integer.parseInt(values[quantityIndex]),
                        values[departmentIndex],
                        values[manufacturerIndex],
                        values[technicalSpecsIndex],
                        Integer.parseInt(values[supermarketIdIndex])
                );
                panels.put(panel.getId(), panel);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return panels;
    }


    public final void writePanels(final String path, final List<Panel> panels) {
        try (
                FileWriter fileWriter = new FileWriter(path);
                CSVWriter csvWriter = new CSVWriter(fileWriter)
        ) {
            csvWriter.writeNext(fields);
            for (Panel panel : panels) {
                final int idIndex = 0;
                final int nameIndex = 1;
                final int quantityIndex = 2;
                final int departmentIndex = 3;
                final int manufacturerIndex = 4;
                final int technicalSpecsIndex = 5;
                final int supermarketIdIndex = 6;
                String[] values = new String[]{
                        panel.getId().toString(),
                        panel.getName(),
                        String.valueOf(panel.getQuantity()),
                        panel.getDepartment(),
                        panel.getManufacturer(),
                        panel.getTechnicalSpecifications(),
                        String.valueOf(panel.getSupermarketId())
                };
                csvWriter.writeNext(values);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


