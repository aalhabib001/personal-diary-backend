package bd.info.habib.personaldiarybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiDataResponse<T> {

    int statusCode;

    String message;

    T data;
}
