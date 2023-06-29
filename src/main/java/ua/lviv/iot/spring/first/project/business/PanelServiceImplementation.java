package ua.lviv.iot.spring.first.project.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.spring.first.project.dataaccess.PanelWriter;
import ua.lviv.iot.spring.first.project.rest.model.Panel;

import java.util.List;

@Service
public class PanelServiceImplementation implements PanelService {
    @Autowired
    private PanelWriter panelWriter;

    public final Panel createPanel(final Panel panel) {
        panel.setId(panelWriter.getLastId() + 1);
        return panelWriter.save(panel);
    }

    public final Panel remove(final Integer panelId) {
        Panel panel = panelWriter.findById(panelId);
        if (panel != null) {
            panelWriter.delete(panel);
        }
        return panel;
    }

    public final List<Panel> findAllPanels() {
        return panelWriter.findAll();
    }

    public final Panel findPanelById(final Integer panelId) {
        return panelWriter.findById(panelId);
    }

    public final Panel updatePanel(final Integer id, final Panel panel) {
        panel.setId(id);
        return panelWriter.update(panel);
    }
}
