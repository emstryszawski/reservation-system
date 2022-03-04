package dk.bec.polonez.reservationsystem.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationUserDto {
    private Long id;

    private String name;

    private String email;
}
