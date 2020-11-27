package br.com.softcube.javers.models.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class DResponse<T> {

    private int status;
    private T data;

    private DResponse(T data, DResponseStatus status) {
        this.status = status.code;
        this.data = data;
    }

    public static <T> DResponse<T> ok(T data) {
        return new DResponse(data, DResponseStatus.OK);
    }

    @Getter
    @AllArgsConstructor
    private enum DResponseStatus {
        OK(0),
        NOK(1);

        private final int code;
    }
}

