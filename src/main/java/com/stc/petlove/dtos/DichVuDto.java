package com.stc.petlove.dtos;

import com.stc.petlove.entities.embedded.GiaDichVu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DichVuDto {
    // mã dịch vụ không được trùng
    private String maDichVu;


    private String tenDichVu;

    // nội dung là html
    private String noiDung;

    // Giá dịch vụ phụ thuộc vào loại thú cưng và cân nặng của thú cưng
    private List<GiaDichVu> giaDichVus = new ArrayList<>();
}
