package vn.quynv.springframework.domain.file;

import lombok.*;
import vn.quynv.springframework.domain.client.Client;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BatchFile {
    private String name;
    private String absolutePath;
    private Long size;
    private LocalDateTime modifiedDate;
    private Client client;
}
