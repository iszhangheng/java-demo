package cn.iszh.core;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @version: V1.0
 * @author: Mr.Zhang
 * @className: CreateStream
 * @packageName: cn.iszh.core
 * @description: 创建流的方法
 * @date: 2019-09-29 9:13
 **/
public class CreateStream {
    // 1，通过集合创建
    public static Stream createStreamByArray() {
        List<User> list = Arrays.asList(new User("zhangsan", 18),
                new User("lisi", 19),
                new User("wangwu", 20),
                new User("zhaoliu", 21));
        Stream<User> stream = list.stream();
        return stream;
    }
}
