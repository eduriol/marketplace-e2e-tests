package entities;

import lombok.Data;

@Data
public class Buyer {

    private Long id;
    private String name;
    private Purchase[] purchasedItems;
    private Store preferredStore;

}
