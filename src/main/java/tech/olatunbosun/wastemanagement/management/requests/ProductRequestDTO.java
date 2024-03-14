package tech.olatunbosun.wastemanagement.management.requests;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Data;

/**
 * @author olulodeolatunbosun
 * @created 12/03/2024/03/2024 - 13:14
 */

@Data

public class ProductRequestDTO {



    @NotBlank(message = "Product name is required")
    private  String name;

    @Null
    private  String description;

}
