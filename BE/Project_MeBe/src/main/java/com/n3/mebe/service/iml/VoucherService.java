package com.n3.mebe.service.iml;


import com.n3.mebe.dto.request.voucher.VoucherRequest;
import com.n3.mebe.dto.response.voucher.VoucherResponse;
import com.n3.mebe.entity.Voucher;
import com.n3.mebe.exception.AppException;
import com.n3.mebe.exception.ErrorCode;
import com.n3.mebe.repository.IVoucherRepository;
import com.n3.mebe.service.IVoucherService;
import com.n3.mebe.util.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VoucherService implements IVoucherService {


    @Autowired
    private IVoucherRepository  iVoucherRepository;

    // <editor-fold default state="collapsed" desc="get Voucher By Id">
    @Override
    public Voucher getVoucherById(int id) {
        return iVoucherRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.VOUCHER_NO_EXIST));
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="get Voucher By Code">
    @Override
    public Voucher getVoucherByCode(String code) {
        Voucher voucher = iVoucherRepository.findByVoucherCode(code);
        if(voucher != null){
            return voucher;
        }else {
            throw new AppException(ErrorCode.VOUCHER_CODE_NO_EXIST);
        }
    }
    // </editor-fold>


    /**
     *  Request from Client
     *
     */

    // <editor-fold default state="collapsed" desc="Create Voucher">
    @Override
    public boolean createVoucher(VoucherRequest request) {
        Voucher voucher = new Voucher();

        //Nếu code bằng null thì tự tạo mã rồi add vào request để save
        if(request.getCode() == null){
            String code;
            do {
                code = DataUtils.generateCode(10);
            } while (iVoucherRepository.existsByVoucherCode(code));
            request.setCode(code);
        }else {
            if(iVoucherRepository.existsByVoucherCode(request.getCode())){
                throw new AppException(ErrorCode.VOUCHER_CODE_EXIST);
            }
        }
        voucher.setVoucherCode(request.getCode());
        voucher.setDiscountType(request.getDiscountType());
        voucher.setDiscountValue(request.getDiscountValue());
        voucher.setCost(request.getCost());
        voucher.setQuantity(request.getQuantity());
        voucher.setMinimumApply(request.getMinimumApply());
        voucher.setMaxDiscount(request.getMaxDiscount());
        voucher.setActive(request.isActive());
        voucher.setPublic(request.isPublic());
        voucher.setStartDate(request.getStartDate());
        voucher.setEndDate(request.getEndDate());
        Date now = new Date();
        voucher.setCreateAt(now);
        voucher.setUpdateAt(now);
        iVoucherRepository.save(voucher);
        return true;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Update Voucher">
    @Override
    public boolean updateVoucher(int id, VoucherRequest request) {
        Voucher voucher = getVoucherById(id);
        boolean check = false;

        if(voucher != null){
            //Nếu code bằng null thì tự tạo mã rồi add vào request để save
            if(request.getCode() == null){
                String code;
                do {
                    code = DataUtils.generateCode(10);
                } while (iVoucherRepository.existsByVoucherCode(code));
                request.setCode(code);
            }else {
                if(iVoucherRepository.existsByVoucherCode(request.getCode())){
                    throw new AppException(ErrorCode.VOUCHER_CODE_EXIST);
                }
            }
            voucher.setVoucherCode(request.getCode());
            voucher.setDiscountType(request.getDiscountType());
            voucher.setDiscountValue(request.getDiscountValue());
            voucher.setCost(request.getCost());
            voucher.setQuantity(request.getQuantity());
            voucher.setMinimumApply(request.getMinimumApply());
            voucher.setMaxDiscount(request.getMaxDiscount());
            voucher.setActive(request.isActive());
            voucher.setPublic(request.isPublic());
            voucher.setStartDate(request.getStartDate());
            voucher.setEndDate(request.getEndDate());
            Date now = new Date();
            voucher.setCreateAt(now);
            voucher.setUpdateAt(now);
            iVoucherRepository.save(voucher);
            check = true;
        }
        return check;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Set Active">
    @Override
    public boolean setActive(int id, boolean status) {
        boolean check = false;
        Voucher voucher = getVoucherById(id);
        if(voucher != null){
            voucher.setActive(status);
            Date now = new Date();
            voucher.setUpdateAt(now);
            iVoucherRepository.save(voucher);
            check = true;
        }
        return check;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Set Public">
    @Override
    public boolean setPublic(int id, boolean status) {
        boolean check = false;
        Voucher voucher = getVoucherById(id);
        if(voucher != null){
            voucher.setPublic(status);
            Date now = new Date();
            voucher.setUpdateAt(now);
            iVoucherRepository.save(voucher);
            check = true;
        }
        return check;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Delete Voucher">
    @Override
    public void deleteVoucher(int id) {
        iVoucherRepository.deleteById(id);
    }// </editor-fold>



    /**
     *  Response from Client
     *
     */

    // <editor-fold default state="collapsed" desc="get Voucher By Id Response">
    @Override
    public VoucherResponse getVoucherByIdResponse(int id) {

        Voucher voucher = getVoucherById(id);

        VoucherResponse voucherResponse = new VoucherResponse();

        voucherResponse.setVoucherId(voucher.getVoucherId());
        voucherResponse.setVoucherCode(voucher.getVoucherCode());
        voucherResponse.setDiscountType(voucher.getDiscountType());
        voucherResponse.setDiscountValue(voucherResponse.getDiscountValue());
        voucherResponse.setName(voucher.getName());
        voucherResponse.setCost(voucher.getCost());
        voucherResponse.setQuantity(voucher.getQuantity());
        voucherResponse.setMinimumApply(voucher.getMinimumApply());
        voucherResponse.setMaxDiscount(voucher.getMaxDiscount());
        voucherResponse.setActive(voucher.isActive());
        voucherResponse.setPublic(voucher.isPublic());
        voucherResponse.setStartDate(voucher.getStartDate());
        voucherResponse.setEndDate(voucher.getEndDate());
        voucherResponse.setCreateAt(voucher.getCreateAt());
        voucherResponse.setUpdateAt(voucher.getUpdateAt());

        return voucherResponse;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="get Voucher By Code Response">
    @Override
    public VoucherResponse getVoucherByCodeResponse(String code) {

        Voucher voucher = getVoucherByCode(code);

        VoucherResponse voucherResponse = new VoucherResponse();

        voucherResponse.setVoucherId(voucher.getVoucherId());
        voucherResponse.setVoucherCode(voucher.getVoucherCode());
        voucherResponse.setDiscountType(voucher.getDiscountType());
        voucherResponse.setDiscountValue(voucherResponse.getDiscountValue());
        voucherResponse.setName(voucher.getName());
        voucherResponse.setCost(voucher.getCost());
        voucherResponse.setQuantity(voucher.getQuantity());
        voucherResponse.setMinimumApply(voucher.getMinimumApply());
        voucherResponse.setMaxDiscount(voucher.getMaxDiscount());
        voucherResponse.setActive(voucher.isActive());
        voucherResponse.setPublic(voucher.isPublic());
        voucherResponse.setStartDate(voucher.getStartDate());
        voucherResponse.setEndDate(voucher.getEndDate());
        voucherResponse.setCreateAt(voucher.getCreateAt());
        voucherResponse.setUpdateAt(voucher.getUpdateAt());

        return voucherResponse;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Search Voucher By Name">
    @Override
    public List<VoucherResponse> searchVoucherByName(String name) {
        List<Voucher> list = iVoucherRepository.searchByName(name);

        List<VoucherResponse> responseList = new ArrayList<>();

        for(Voucher voucher :list){
            VoucherResponse voucherResponse = new VoucherResponse();

            voucherResponse.setVoucherId(voucher.getVoucherId());
            voucherResponse.setVoucherCode(voucher.getVoucherCode());
            voucherResponse.setDiscountType(voucher.getDiscountType());
            voucherResponse.setDiscountValue(voucherResponse.getDiscountValue());
            voucherResponse.setName(voucher.getName());
            voucherResponse.setCost(voucher.getCost());
            voucherResponse.setQuantity(voucher.getQuantity());
            voucherResponse.setMinimumApply(voucher.getMinimumApply());
            voucherResponse.setMaxDiscount(voucher.getMaxDiscount());
            voucherResponse.setActive(voucher.isActive());
            voucherResponse.setPublic(voucher.isPublic());
            voucherResponse.setStartDate(voucher.getStartDate());
            voucherResponse.setEndDate(voucher.getEndDate());
            voucherResponse.setCreateAt(voucher.getCreateAt());
            voucherResponse.setUpdateAt(voucher.getUpdateAt());

            responseList.add(voucherResponse);
        }
        return responseList;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="get List Voucher Response All">
    @Override
    public List<VoucherResponse> getListVoucherResponseAll() {
        List<Voucher> list = iVoucherRepository.findAll();

        List<VoucherResponse> responseList = new ArrayList<>();

        for(Voucher voucher :list){
            VoucherResponse voucherResponse = new VoucherResponse();

            voucherResponse.setVoucherId(voucher.getVoucherId());
            voucherResponse.setVoucherCode(voucher.getVoucherCode());
            voucherResponse.setDiscountType(voucher.getDiscountType());
            voucherResponse.setDiscountValue(voucherResponse.getDiscountValue());
            voucherResponse.setName(voucher.getName());
            voucherResponse.setCost(voucher.getCost());
            voucherResponse.setQuantity(voucher.getQuantity());
            voucherResponse.setMinimumApply(voucher.getMinimumApply());
            voucherResponse.setMaxDiscount(voucher.getMaxDiscount());
            voucherResponse.setActive(voucher.isActive());
            voucherResponse.setPublic(voucher.isPublic());
            voucherResponse.setStartDate(voucher.getStartDate());
            voucherResponse.setEndDate(voucher.getEndDate());
            voucherResponse.setCreateAt(voucher.getCreateAt());
            voucherResponse.setUpdateAt(voucher.getUpdateAt());

            responseList.add(voucherResponse);
        }
        return responseList;
    }// </editor-fold>


}
