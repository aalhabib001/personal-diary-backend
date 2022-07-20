package bd.info.habib.personaldiarybackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiMessageResponse {

    int statusCode;

    String message;

}
