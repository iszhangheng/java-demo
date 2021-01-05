package cn.iszhangheng.entity;

public class CommonResult {
    public static CommonResult success(int count) {
        return null;
    }

    public static CommonResult failed() {
        return null;
    }

    public static Object forbidden(String message) {
        return null;
    }
}
