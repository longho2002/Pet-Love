package com.stc.petlove.dtos;

import lombok.Data;

@Data
public class UpdateProfileDto {
    public String name;
    public String email;
    public String password;
    public String dienThoai;
}
