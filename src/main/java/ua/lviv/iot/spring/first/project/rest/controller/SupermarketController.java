package ua.lviv.iot.spring.first.project.rest.controller;

import org.springframework.http.ResponseEntity;
import ua.lviv.iot.spring.first.project.rest.model.Supermarket;

import java.util.List;

public interface SupermarketController {
    List<Supermarket> getSupermarkets();

    Supermarket getSupermarket(Integer supermarketId);

    Supermarket createSupermarket(Supermarket supermarket);

    ResponseEntity<Supermarket> deleteSupermarket(Integer supermarketId);

    Supermarket updateSupermarket(Integer supermarketId,
                                  Supermarket supermarket);
}
