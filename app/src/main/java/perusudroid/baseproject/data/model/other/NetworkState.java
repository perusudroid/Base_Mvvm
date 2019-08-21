package perusudroid.baseproject.data.model.other;

public class NetworkState {

    private Status status;
    private String msg;

    public NetworkState() {
    }

    public static NetworkState loading() {
        return new NetworkState(Status.LOADING, "");
    }

    public static NetworkState loaded() {
        return new NetworkState(Status.LOADED, "");
    }

    public static NetworkState empty(String msg) {
        return new NetworkState(Status.EMPTY, msg);
    }

    public static NetworkState error(String msg) {
        return new NetworkState(Status.FAILED, msg);
    }


    public NetworkState(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public enum Status {
        LOADING,
        LOADED, // New
        EMPTY, // New
        FAILED,
        NO_LOAD_MORE
    }
}
