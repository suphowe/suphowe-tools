package com.soft.web.entity;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * 提交表单
 * @author suphowe
 */
@Data
public class SubmitForm {

    @NotNull(message = "ID不能为空")
    String id;

    @NotEmpty(message = "姓名必填")
    String username;

    @NotBlank(message = "地址必填")
    String address;

    @Size(min = 5, max =10, message = "5~10")
    String job;

    @Min(100)
    int height;

    @Max(300)
    int weight;

    @Email
    String email;

}
