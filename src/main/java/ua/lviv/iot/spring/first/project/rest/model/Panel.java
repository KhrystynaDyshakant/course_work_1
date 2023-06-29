package ua.lviv.iot.spring.first.project.rest.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Panel {
    private Integer id;
    private String name;
    private int quantity;
    private String department;
    private String manufacturer;
    private String technicalSpecifications;
    private int supermarketId;

}