package vn.quynv.springframework.webflux.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@RequiredArgsConstructor
@Table("tasks")
@ToString
public class Task {
    @Id
    private Integer id;
    private String description;
    private Boolean completed;
}
