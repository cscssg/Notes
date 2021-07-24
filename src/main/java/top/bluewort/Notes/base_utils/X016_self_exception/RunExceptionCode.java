package top.bluewort.Notes.base_utils.X016_self_exception;

/**
 * 任务执行异常
 */
public enum RunExceptionCode {
    LINK_EXCEPTION(601),//链接异常
    FIELD_EXCEPTION(602),//字段异常
    DATA_DUPLICATION(603),//数据重复
    OTHER_EXCEPTION(609);//其它异常

    private final int code;

    RunExceptionCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
