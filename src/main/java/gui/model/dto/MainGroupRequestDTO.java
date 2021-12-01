package gui.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MainGroupRequestDTO {
    private LocalDateTime dateCreate;
    private String whoCreate;
    private Long laboratory;
    private String ipAddressCreate;
    private List<MainValueDTO> mainValueDTOList;
}
