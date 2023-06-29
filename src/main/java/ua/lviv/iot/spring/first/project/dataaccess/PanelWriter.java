package ua.lviv.iot.spring.first.project.dataaccess;

import ua.lviv.iot.spring.first.project.rest.model.Panel;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface PanelWriter {
    List<Panel> findAll();

    Panel findById(Integer panelId);

    Panel save(Panel panel);

    void delete(Panel panel);

    int getLastId();

    Panel update(Panel panel);

    Map<Integer, Panel> loadPanels(String path);

    void writePanels(String path, List<Panel> panels);

    List<String> getMonthPaths(String basePath, LocalDateTime date)
            throws IOException;
}
