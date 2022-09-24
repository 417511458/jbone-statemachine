package cn.jbone.statemachine.core;

import java.io.IOException;

/**
 * 支持错误码的状态机异常
 */
public class StateMachineException extends RuntimeException {
    /**
     * 错误码
     */
    private int code;
    public StateMachineException(IOException e) {
        super(e);
    }

    public StateMachineException(String message, Exception e) {
        super(message, e);
    }

    public StateMachineException(String message, Throwable cause) {
        super(message, cause);
    }

    public StateMachineException(String message) {
        super(message);
    }

    /**
     * 带错误码的构造函数
     * @param code 错误码
     * @param message 错误信息
     */
    public StateMachineException(int code,String message){
        super(message);
        this.code = code;
    }

    /**
     * 带错误码的构造函数
     * @param code 错误码
     * @param message 错误信息
     */
    public StateMachineException(int code,String message, Exception e) {
        super(message, e);
        this.code = code;
    }

    /**
     * 带错误码的构造函数
     * @param code 错误码
     * @param message 错误信息
     */
    public StateMachineException(int code,String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
