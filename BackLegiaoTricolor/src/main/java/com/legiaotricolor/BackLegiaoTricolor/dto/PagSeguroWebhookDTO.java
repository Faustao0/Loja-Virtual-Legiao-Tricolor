package com.legiaotricolor.BackLegiaoTricolor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PagSeguroWebhookDTO {

    private String id;

    @JsonProperty("reference_id")
    private String referenceId;

    private String status;
}
