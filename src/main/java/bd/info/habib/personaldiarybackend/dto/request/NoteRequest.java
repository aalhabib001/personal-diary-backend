package bd.info.habib.personaldiarybackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoteRequest {

    private String title;

    private String content;

    private Long categoryId;

}
