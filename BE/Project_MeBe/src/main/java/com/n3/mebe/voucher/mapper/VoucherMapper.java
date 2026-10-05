package com.n3.mebe.voucher.mapper;

import com.n3.mebe.voucher.dto.response.VoucherResponse;
import com.n3.mebe.voucher.entity.Voucher;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherMapper {

    private final ModelMapper modelMapper;

    public VoucherMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VoucherResponse toResponse(Voucher voucher) {
        return modelMapper.map(voucher, VoucherResponse.class);
    }

    public List<VoucherResponse> toResponseList(List<Voucher> vouchers) {
        return vouchers.stream().map(this::toResponse).toList();
    }
}
