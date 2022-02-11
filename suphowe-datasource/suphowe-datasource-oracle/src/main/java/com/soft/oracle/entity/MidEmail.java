package com.soft.oracle.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MidEmail {

    String empl_id;
    String email_type;
    String addr;
    String primary;
    String create_dttm;
    String create_oprid;
    String create_type;

}
