package ua.lviv.iot.spring.first.project.business;

import ua.lviv.iot.spring.first.project.rest.model.Supermarket;

import java.util.List;

public interface SupermarketService {

    Supermarket createSupermarket(Supermarket supermarket);

    Supermarket remove(Integer supermarketId);

    List<Supermarket> findAllSupermarkets();

    Supermarket findSupermarketById(Integer supermarketId);

    Supermarket updateSupermarket(Integer id, Supermarket supermarket);

}
