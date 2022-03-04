package dk.bec.polonez.reservationsystem.dto.offer;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateDiscountDto {

    @NotBlank(message = "name is mandatory")
    private String name;
    @NotBlank(message = "code is mandatory")
    private String code;
    @NotBlank(message = "value is mandatory")
    private long valueInPercentage;

    private long dateFrom;

    private long dateTo;
    @NotBlank(message = "offer is mandatory")
    private Long offerId;

}