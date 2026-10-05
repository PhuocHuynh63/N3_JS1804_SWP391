package com.n3.mebe.voucher.service;


import com.n3.mebe.voucher.dto.request.VoucherRequest;
import com.n3.mebe.voucher.dto.response.VoucherResponse;
import com.n3.mebe.voucher.entity.Voucher;

import java.util.List;

public interface IVoucherService {

    Voucher getVoucherById(int id);

    Voucher getVoucherByCode(String code);

    boolean checkUsedVoucher(String code, int userId);

    boolean createVoucher(VoucherRequest request);

    boolean updateVoucher(int id , VoucherRequest request);

    boolean setActive(int id, boolean status);

    boolean setPublic(int id, boolean status);

    void deleteVoucher(int id);

    VoucherResponse getVoucherByIdResponse(int id);

    VoucherResponse getVoucherByCodeResponse(String code);

    List<VoucherResponse> searchVoucherByName(String name);

    List<VoucherResponse> getListVoucherResponseAll();
}
