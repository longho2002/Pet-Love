package com.stc.petlove.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class TaiKhoanDto {
    private String name;
    private String email;
    private String password;
    private String dienThoai;
}
