package ua.lviv.iot.spring.first.project.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ua.lviv.iot.spring.first.project.business.SupermarketService;
import ua.lviv.iot.spring.first.project.rest.model.Supermarket;

import java.util.List;

@RequestMapping("/supermarkets")
@RestController
public class SupermarketControllerImplementation implements SupermarketController {
    private static int lastId = 0;
    @Autowired
    private SupermarketService supermarketService;

    @GetMapping
    public final List<Supermarket> getSupermarkets() {
        return supermarketService.findAllSupermarkets();
    }

    @GetMapping(path = "/{id}")
    public final Supermarket getSupermarket(final @PathVariable("id")
                                                Integer supermarketId) {
        return supermarketService.findSupermarketById(supermarketId);
    }
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public final Supermarket createSupermarket(final @RequestBody
                                                   Supermarket supermarket) {
        supermarket.setId(++lastId);
        return supermarketService.createSupermarket(supermarket);
    }

    @DeleteMapping(path = "/{id}")
    public final ResponseEntity<Supermarket> deleteSupermarket(
            final @PathVariable("id") Integer supermarketId) {

        HttpStatus status = supermarketService.remove(supermarketId)
                == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(status).build();
    }

    @PutMapping(path = "/{id}")
    public final Supermarket updateSupermarket(final @PathVariable("id")
                                                   Integer supermarketId,
                                               final @RequestBody Supermarket
                                                       supermarket) {
        return supermarketService.updateSupermarket(supermarketId, supermarket);
    }
}
