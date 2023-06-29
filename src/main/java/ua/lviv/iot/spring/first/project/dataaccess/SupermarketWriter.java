package ua.lviv.iot.spring.first.project.dataaccess;

import ua.lviv.iot.spring.first.project.rest.model.Supermarket;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SupermarketWriter {
    List<Supermarket> findAll();

    Supermarket findById(Integer supermarketId);

    Supermarket save(Supermarket supermarket);

    void delete(Supermarket supermarket);

    Supermarket update(Supermarket supermarket);

    int getLastId();

    Map<Integer, Supermarket> loadSupermarkets(String path);

    void writeSupermarkets(String path, List<Supermarket> supermarkets);

    List<String> getMonthPaths(String basePath, LocalDateTime date)
            throws IOException;
}
