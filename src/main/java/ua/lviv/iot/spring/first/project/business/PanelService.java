package ua.lviv.iot.spring.first.project.business;
import ua.lviv.iot.spring.first.project.rest.model.Panel;

import java.util.List;

public interface PanelService {

    Panel createPanel(Panel panel);

    Panel remove(Integer panelId);

    List<Panel> findAllPanels();

    Panel findPanelById(Integer panelId);

    Panel updatePanel(Integer id, Panel panel);

}
