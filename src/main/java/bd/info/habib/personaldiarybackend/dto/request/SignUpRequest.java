package bd.info.habib.personaldiarybackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpRequest {

    private String password;

    private String name;

    private String userName;

}