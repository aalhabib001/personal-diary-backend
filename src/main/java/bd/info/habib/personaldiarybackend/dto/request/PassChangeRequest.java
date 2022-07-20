package bd.info.habib.personaldiarybackend.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class PassChangeRequest {

    String oldPass;

    String newPass;

    String confirmPass;
}
