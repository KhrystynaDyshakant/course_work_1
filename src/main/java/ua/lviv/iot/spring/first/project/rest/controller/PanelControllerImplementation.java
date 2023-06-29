package ua.lviv.iot.spring.first.project.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ua.lviv.iot.spring.first.project.business.PanelService;
import ua.lviv.iot.spring.first.project.rest.model.Panel;

import java.util.List;

@RequestMapping("/panels")
@RestController
public class PanelControllerImplementation implements PanelController {
    private static int lastId = 0;
    @Autowired
    private PanelService panelService;

    @GetMapping
    public final List<Panel> getPanels() {
        return panelService.findAllPanels();
    }

    @GetMapping(path = "/{id}")
    public final Panel getPanel(final @PathVariable("id") Integer panelId) {
        return panelService.findPanelById(panelId);
    }

    @PostMapping
    public final Panel createPanel(final @RequestBody Panel panel) {
        panel.setId(++lastId);
        return panelService.createPanel(panel);
    }

    @DeleteMapping(path = "/{id}")
    public final ResponseEntity<Panel> deletePanel(final @PathVariable("id")
                                                       Integer panelId) {

        HttpStatus status = panelService.remove(panelId)
                == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(status).build();
    }

    @PutMapping(path = "/{id}")
    public final Panel updatePanel(final @PathVariable("id") Integer panelId,
                             final @RequestBody Panel panel) {
        return panelService.updatePanel(panelId, panel);
    }
}
