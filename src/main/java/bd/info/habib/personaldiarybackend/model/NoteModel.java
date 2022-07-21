package bd.info.habib.personaldiarybackend.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NOTES")
public class NoteModel extends BaseEntity {

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private CategoryModel category;

}
