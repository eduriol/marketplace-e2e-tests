package entities;

import lombok.Data;

@Data
public class Item {

    private Long id;
    private String title;
    private Store store;

}
