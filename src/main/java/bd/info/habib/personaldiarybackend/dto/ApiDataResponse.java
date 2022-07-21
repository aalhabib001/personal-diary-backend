package bd.info.habib.personaldiarybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiDataResponse<T> extends ApiMessageResponse {

    T data;

    public ApiDataResponse(int statusCode, String message, T data) {
        super(statusCode, message);
        this.data = data;
    }
}
