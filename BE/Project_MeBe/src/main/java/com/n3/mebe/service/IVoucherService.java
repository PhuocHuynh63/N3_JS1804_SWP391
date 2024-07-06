package com.n3.mebe.service;


import com.n3.mebe.dto.request.voucher.VoucherRequest;
import com.n3.mebe.dto.response.voucher.VoucherResponse;
import com.n3.mebe.entity.Voucher;

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
