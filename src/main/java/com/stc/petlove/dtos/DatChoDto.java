package com.stc.petlove.dtos;

import com.stc.petlove.entities.embedded.ThongTinDatCho;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DatChoDto {
    // email người đặt chỗ
    public String email;

    public List<ThongTinDatCho> thongTinDatChos = new ArrayList<>();

    // Thời gian chăm sóc thú cưng
    public Date thoiGian;

    // căn dặn khi chăm sóc thú cưng
    public String canDan;

    // lấy từ enum trạng thái đặt chỗ
    public String trangThaiDatCho;
}
