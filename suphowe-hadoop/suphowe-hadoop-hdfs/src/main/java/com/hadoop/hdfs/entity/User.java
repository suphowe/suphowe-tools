package com.hadoop.hdfs.entity;

import lombok.Data;
import lombok.ToString;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 用户实体类
 * @author suphowe
 */
@Data
@ToString
public class User implements Writable {

    private String username;

    private Integer age;

    private String address;

    public User() {
        super();
    }

    public User(String username, Integer age, String address) {
        super();
        this.username = username;
        this.age = age;
        this.address = address;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        // 把对象序列化
        output.writeChars(username);
        output.writeInt(age);
        output.writeChars(address);
    }

    @Override
    public void readFields(DataInput input) throws IOException {
        // 把序列化的对象读取到内存中
        username = input.readUTF();
        age = input.readInt();
        address = input.readUTF();
    }
}
