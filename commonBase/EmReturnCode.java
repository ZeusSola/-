package com.yuchen.catalog.common.constants;

/**
 * 接口返回代码 枚举
 */
public enum EmReturnCode {
    SUCCESS("成功",1),
    FAIL("失败",-1);
    // 成员变量
    private String name;
    private int value;

    // 构造方法
    private EmReturnCode(String name, int value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(int value) {
        for (EmReturnCode c : EmReturnCode.values()) {
            if (c.getValue() == value) {
                return c.name;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
