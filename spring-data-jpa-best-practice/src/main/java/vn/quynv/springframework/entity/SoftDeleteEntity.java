package vn.quynv.springframework.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Setter
@Getter
public class SoftDeleteEntity {
    @Column(name="deleted")
    protected Boolean deleted = new Boolean(false);
}
