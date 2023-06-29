package ua.lviv.iot.spring.first.project.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.spring.first.project.dataaccess.SupermarketWriter;
import ua.lviv.iot.spring.first.project.rest.model.Supermarket;

import java.util.List;

@Service
public class SupermarketServiceImplementation implements SupermarketService {
    @Autowired
    private SupermarketWriter supermarketWriter;

    public final Supermarket createSupermarket(final Supermarket supermarket) {
        supermarket.setId(supermarketWriter.getLastId() + 1);
        return supermarketWriter.save(supermarket);
    }

    public final Supermarket remove(final Integer supermarketId) {
        Supermarket supermarket = supermarketWriter.findById(supermarketId);
        if (supermarket != null) {
            supermarketWriter.delete(supermarket);
        }
        return supermarket;
    }

    public final List<Supermarket> findAllSupermarkets() {
        return supermarketWriter.findAll();
    }

    public final Supermarket findSupermarketById(final Integer supermarketId) {
        return supermarketWriter.findById(supermarketId);
    }

    public final Supermarket updateSupermarket(final Integer id,
                                               final Supermarket supermarket) {
        supermarket.setId(id);
        return supermarketWriter.update(supermarket);
    }
}
