package asi.saga.demo.order.model;

public class MessageInfo {
    public MessageInfo(boolean b, String s) {
        this.success = b;
        this.msg = s;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    private Boolean success;
    private String msg;


}
