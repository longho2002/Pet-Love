package com.stc.petlove.dtos;

import lombok.Data;

@Data
public class LoaiThuCungDto {

    // mã không được trùng
    private String maLoaiThuCung;

    private String tenLoaiThuCung;
}
