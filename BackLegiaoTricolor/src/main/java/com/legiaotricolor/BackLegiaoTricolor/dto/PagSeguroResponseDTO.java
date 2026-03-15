package com.legiaotricolor.BackLegiaoTricolor.dto;

import lombok.Data;
import java.util.List;

@Data
public class PagSeguroResponseDTO {

    private String id;
    private String status;

    private List<QrCode> qrCodes;

    @Data
    public static class QrCode {
        private String text;
        private List<Link> links;
    }

    @Data
    public static class Link {
        private String href;
    }
}