package com.stc.petlove.services.TaiKhoan;

import com.stc.petlove.dtos.TaiKhoanDto;
import com.stc.petlove.entities.TaiKhoan;
import com.stc.petlove.exceptions.InvalidException;
import com.stc.petlove.exceptions.NotFoundException;
import com.stc.petlove.repositories.TaiKhoanRepository;
import com.stc.petlove.utils.MapperUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TaiKhoanService implements ITaiKhoanService {
    private final TaiKhoanRepository taiKhoanRepository;

    public TaiKhoanService(TaiKhoanRepository taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }

    @Async
    @Override
    public CompletableFuture<TaiKhoan> create(TaiKhoanDto input) {
        TaiKhoan tk = taiKhoanRepository.getUser(input.getEmail()).orElse(null);
        if (tk != null)
            throw new InvalidException("Email đã tồn tại");
        tk = new TaiKhoan();
        MapperUtils.toDto(input, tk);
        tk = taiKhoanRepository.save(tk);
        return CompletableFuture.completedFuture(tk);
    }

    @Async
    @Override
    public CompletableFuture<TaiKhoan> getOne(String id) {
        TaiKhoan tk = taiKhoanRepository.findById(id).orElse(null);
        if (tk == null) {
            throw new NotFoundException("Không tìm thấy tài khoản");
        }
        return CompletableFuture.completedFuture(tk);
    }

    @Async
    @Override
    public CompletableFuture<List<TaiKhoan>> getAll() {
        return CompletableFuture.completedFuture(taiKhoanRepository.findAll());
    }

    @Async
    @Override
    public CompletableFuture<TaiKhoan> update(String id, TaiKhoan data) {
        TaiKhoan tk = taiKhoanRepository.findById(id).orElse(null);
        if (tk == null) {
            throw new NotFoundException("Không tìm thấy tài khoản");
        }
        if (data.getEmail().equals(tk.getEmail()))
            throw new InvalidException("Email đã tồn tại");
        MapperUtils.toDto(data, tk);
        taiKhoanRepository.save(data);
        return CompletableFuture.completedFuture(data);
    }

    @Async
    @Override
    public CompletableFuture<Void> remove(String id) {
        TaiKhoan tk = taiKhoanRepository.findById(id).orElse(null);
        if (tk == null) {
            throw new NotFoundException("Không tìm thấy tài khoản");
        }
        tk.setTrangThai(false);
        taiKhoanRepository.save(tk);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    @Override
    public CompletableFuture<TaiKhoan> setTrangThai(String id, boolean trangThai) {
        TaiKhoan tk = taiKhoanRepository.findById(id).orElse(null);
        if (tk == null) {
            throw new NotFoundException("Không tìm thấy tài khoản");
        }
        tk.setTrangThai(trangThai);
        tk = taiKhoanRepository.save(tk);
        return CompletableFuture.completedFuture(tk);
    }

    @Override
    public CompletableFuture<TaiKhoan> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String email = "";
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        TaiKhoan tk = taiKhoanRepository.getUser(email).orElse(null);
        if (tk == null) {
            throw new NotFoundException("Không tìm thấy tài khoản");
        }
        return CompletableFuture.completedFuture(tk);
    }

    @Override
    public CompletableFuture<TaiKhoan> updateProfile(TaiKhoan taiKhoan) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String email = "";
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        TaiKhoan tk = taiKhoanRepository.getUser(email).orElse(null);
        if (tk == null) {
            throw new NotFoundException("Không tìm thấy tài khoản");
        }
        if (taiKhoan.getEmail().equals(tk.getEmail()))
            throw new InvalidException("Email đã tồn tại");
        MapperUtils.toDto(taiKhoan, tk);
        taiKhoanRepository.save(taiKhoan);
        return CompletableFuture.completedFuture(taiKhoan);
    }
}
