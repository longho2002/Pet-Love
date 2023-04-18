package com.stc.petlove.services.TaiKhoan;

import com.stc.petlove.dtos.TaiKhoanDto;
import com.stc.petlove.dtos.UpdateProfileDto;
import com.stc.petlove.entities.TaiKhoan;
import com.stc.petlove.exceptions.InvalidException;
import com.stc.petlove.exceptions.NotFoundException;
import com.stc.petlove.repositories.TaiKhoanRepository;
import com.stc.petlove.securities.JwtTokenUtils;
import com.stc.petlove.utils.MapperUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

@Service
public class TaiKhoanService implements ITaiKhoanService {
    private final TaiKhoanRepository taiKhoanRepository;
    private final MongoTemplate mongoTemplate;

    public TaiKhoanService(TaiKhoanRepository taiKhoanRepository, MongoTemplate mongoTemplate) {
        this.taiKhoanRepository = taiKhoanRepository;
        this.mongoTemplate = mongoTemplate;
    }


    @Async
    @Override
    public CompletableFuture<TaiKhoan> create(TaiKhoanDto input) {
        TaiKhoan tk = taiKhoanRepository.getUser(input.getEmail()).orElse(null);
        if (tk != null)
            throw new InvalidException("Email đã tồn tại");
        tk = new TaiKhoan();
        MapperUtils.toDto(input, tk);
        tk.getRoles().add("ROLE_USER");
        tk.setPassword(JwtTokenUtils.hashPassword(input.getPassword()));
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
        if (data.getEmail().equals(tk.getEmail()) && !data.getId().equals(tk.getId()))
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

    @Async
    @Override
    public CompletableFuture<TaiKhoan> getProfile() {
        TaiKhoan tk = taiKhoanRepository.getUser(getUserNameFromToken()).orElse(null);
        if (tk == null) {
            throw new NotFoundException("Không tìm thấy tài khoản");
        }
        return CompletableFuture.completedFuture(tk);
    }

    @Async
    @Override
    public CompletableFuture<TaiKhoan> updateProfile(UpdateProfileDto taiKhoan) {

        TaiKhoan tk = taiKhoanRepository.getUser(getUserNameFromToken()).orElse(null);
        if (tk == null) {
            throw new NotFoundException("Không tìm thấy tài khoản");
        }
        if (taiKhoan.getEmail() != null) {
            TaiKhoan checkEmail = taiKhoanRepository.getUser(taiKhoan.getEmail()).orElse(null);
            if (checkEmail != null && checkEmail.getEmail().equals(taiKhoan.getEmail()) && !checkEmail.getId().equals(tk.getId()))
                throw new InvalidException("Email đã tồn tại");
        }
        MapperUtils.toDto(taiKhoan, tk);
        tk = taiKhoanRepository.save(tk);
        return CompletableFuture.completedFuture(tk);
    }

    @Async
    @Override
    public CompletableFuture<List<TaiKhoan>> findTaiKhoanWithPaginationAndSearch(long skip, int limit, String name, String orderBy) {
//        return CompletableFuture.completedFuture(taiKhoanRepository.findTaiKhoanWithPaginationAndSearch(skip, limit, name));

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("name").regex(Pattern.compile(name, Pattern.CASE_INSENSITIVE))),
                Aggregation.sort(orderBy.contains("-") ? Sort.Direction.DESC : Sort.Direction.ASC, orderBy.contains("-") ? orderBy.substring(0, orderBy.indexOf('-')) : orderBy),
                Aggregation.skip(skip),
                Aggregation.limit(limit)
        );
        return CompletableFuture.completedFuture(mongoTemplate.aggregate(aggregation, "user", TaiKhoan.class).getMappedResults());
    }

    @Async
    @Override
    public CompletableFuture<Long> countTaiKhoan(String name) {
        return CompletableFuture.supplyAsync(() -> taiKhoanRepository.countTaiKhoan(name).orElse(0L));

    }

    private String getUserNameFromToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = "";
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        return email;
    }
}
