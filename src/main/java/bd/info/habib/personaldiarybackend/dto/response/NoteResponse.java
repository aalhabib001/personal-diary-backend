package bd.info.habib.personaldiarybackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponse {

    private Long id;

    private String title;

    private String content;

    private CategoryResponse category;

}
