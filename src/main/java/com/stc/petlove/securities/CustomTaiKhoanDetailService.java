package com.stc.petlove.securities;

import com.stc.petlove.entities.TaiKhoan;
import com.stc.petlove.exceptions.InvalidException;
import com.stc.petlove.repositories.TaiKhoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomTaiKhoanDetailService implements UserDetailsService {

    private final TaiKhoanRepository taiKhoanRepository;

    public CustomTaiKhoanDetailService(TaiKhoanRepository userRepository) {
        this.taiKhoanRepository = userRepository;
    }

    @Override
    public JwtUserDetail loadUserByUsername(String email) throws UsernameNotFoundException {
        TaiKhoan user = taiKhoanRepository.getUser(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Tài khoản có email %s không tồn tại", email)));
        if (!user.isTrangThai()) {
            throw new InvalidException("Tài khoản đã bị khóa, vui lòng liên hệ quản trị viên");
        }
        return new JwtUserDetail(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()),
                user.isTrangThai());
    }
}
