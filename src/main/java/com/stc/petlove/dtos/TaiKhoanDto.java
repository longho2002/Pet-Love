package com.stc.petlove.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class TaiKhoanDto {
    public String name;
    public String email;
    public String password;
    public String dienThoai;
}
