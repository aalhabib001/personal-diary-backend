package bd.info.habib.personaldiarybackend.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    @CreatedBy
    @NotNull
    @Column(name = "CREATED_BY", nullable = false, length = 20)
    private String createdBy;

    @CreatedDate
    @NotNull
    @Column(name = "CREATED_ON", nullable = false)
    private LocalDateTime createdOn;

    @LastModifiedBy
    @Column(name = "LAST_UPDATED_BY", length = 20)
    private String lastUpdatedBy;

    @Column(name = "LAST_UPDATED_TIME")
    @LastModifiedDate
    private LocalDateTime lastUpdatedTime;


}
