package ua.lviv.iot.spring.first.project.rest.controller;

import org.springframework.http.ResponseEntity;
import ua.lviv.iot.spring.first.project.rest.model.Panel;

import java.util.List;

public interface PanelController {
    List<Panel> getPanels();

    Panel getPanel(Integer panelId);

    Panel createPanel(Panel panel);

    ResponseEntity<Panel> deletePanel(Integer panelId);

    Panel updatePanel(Integer panelId, Panel panel);
}

