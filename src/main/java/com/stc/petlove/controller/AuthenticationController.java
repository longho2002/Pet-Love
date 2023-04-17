package com.stc.petlove.controller;

import com.stc.petlove.annotations.ApiPrefixController;
import com.stc.petlove.dtos.AccountDto;
import com.stc.petlove.dtos.TokenDetail;
import com.stc.petlove.exceptions.BadRequestException;
import com.stc.petlove.securities.CustomTaiKhoanDetailService;
import com.stc.petlove.securities.JwtTokenUtils;
import com.stc.petlove.securities.JwtUserDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@RestController
@ApiPrefixController("/auth")
public class AuthenticationController {
    private final CustomTaiKhoanDetailService customTaiKhoanDetailService;

    private final JwtTokenUtils jwtTokenUtils;

    public AuthenticationController(CustomTaiKhoanDetailService customTaiKhoanDetailService,
                                    JwtTokenUtils jwtTokenUtils) {
        this.customTaiKhoanDetailService = customTaiKhoanDetailService;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDetail> login(@Valid @RequestBody AccountDto dto) {
        final JwtUserDetail userDetails = customTaiKhoanDetailService
                .loadUserByUsername(dto.getUsername());
        if (!JwtTokenUtils.comparePassword(dto.getPassword(), userDetails.getPassword())){
            throw new BadRequestException("Password not correct");
        }
        final TokenDetail result = jwtTokenUtils.getTokenDetail(userDetails);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/hello")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> sayHello(Principal principal) {
        return new ResponseEntity<>(String.format("Hello %s", principal.getName()), HttpStatus.OK);
    }

}
