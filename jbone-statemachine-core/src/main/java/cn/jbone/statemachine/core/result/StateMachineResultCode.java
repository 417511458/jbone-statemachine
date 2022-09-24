package cn.jbone.statemachine.core.result;

/**
 * 返回结果码
 */
public enum StateMachineResultCode {
    SUCCESS(0,"成功"),

    ERROR(1,"系统异常"),

    DENIED(2,"状态机暂不接受此事件，拒绝访问"),
    ;
    private int code;
    private String message;
    StateMachineResultCode(int code,String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
