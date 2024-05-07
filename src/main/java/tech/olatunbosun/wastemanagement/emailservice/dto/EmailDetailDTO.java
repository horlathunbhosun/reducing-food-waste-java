package tech.olatunbosun.wastemanagement.emailservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author olulodeolatunbosun
 * @created 07/05/2024/05/2024 - 02:16
 */

@Data
@Builder
public class EmailDetailDTO {
   private String to;
   private String subject;
   private Map<String, Object> dynamicValue;
   private String templateName;
}
