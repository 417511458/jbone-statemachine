package cn.jbone.statemachine.core.result;

/**
 * 状态机统一返回结果
 */
public class StateMachineResult {
    /**
     * 返回码
     */
    private int code;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 状态机结果
     */
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StateMachineResult(){
        this.code = StateMachineResultCode.SUCCESS.getCode();
    }
    public StateMachineResult(int code,String message,Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public StateMachineResult(StateMachineResultCode resultCode,Object data){
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public void setResultCode(StateMachineResultCode resultCode){
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    /**
     * 判断结果是否成功
     * @return 如果执行成功，返回true，如果执行失败，返回false
     */
    public boolean isSuccess(){
        return this.code == StateMachineResultCode.SUCCESS.getCode();
    }
}
