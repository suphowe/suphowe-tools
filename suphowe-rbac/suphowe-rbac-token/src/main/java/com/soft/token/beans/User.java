package com.soft.token.beans;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户表
 * @author suphowe
 */
@Data
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 2332502674491868041L;
    String id;

    String username;

    String password;
}
