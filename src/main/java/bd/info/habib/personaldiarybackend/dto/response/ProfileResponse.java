package bd.info.habib.personaldiarybackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {

    private String username;

    private String phoneNo;

    private String name;

}
