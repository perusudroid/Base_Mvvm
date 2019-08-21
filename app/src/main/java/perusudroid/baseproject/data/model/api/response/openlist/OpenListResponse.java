package perusudroid.baseproject.data.model.api.response.openlist;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class OpenListResponse {
    private String status_code;
    private List<Data> data;
    private String message;

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}