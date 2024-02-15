package org.devleopx.kiosk.api.sample;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record ValidationRequest(
        Long id,
        @NotBlank
        String itemname,
        @NotNull @Range(min = 1000, max = 100000)
        Integer price,
        @NotNull @Max(9999)
        Integer quantity
) {
}
