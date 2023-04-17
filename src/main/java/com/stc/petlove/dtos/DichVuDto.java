package com.stc.petlove.dtos;

import com.stc.petlove.entities.embedded.GiaDichVu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DichVuDto {
    // mã dịch vụ không được trùng
    public String maDichVu;


    public String tenDichVu;

    // nội dung là html
    public String noiDung;

    // Giá dịch vụ phụ thuộc vào loại thú cưng và cân nặng của thú cưng
    public List<GiaDichVu> giaDichVus = new ArrayList<>();
}
