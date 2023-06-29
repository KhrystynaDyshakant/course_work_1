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
public class Supermarket {
    private Integer id;
    private String name;
    private String address;
    private int panelId;

}