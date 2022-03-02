package dk.bec.polonez.reservationsystem.dto;

import lombok.Data;

@Data
public class DiscountDto {

    private String name;

    private String code;

    private int value;

    private long dateFrom;

    private long dateTo;

}
