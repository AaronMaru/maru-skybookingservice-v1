package com.skybooking.skyflightservice.v1_0_0.service.implement.backoffice;

import com.skybooking.skyflightservice.constant.TicketConstant;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingAdditionalServiceEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingEntity;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.backoffice.AdditionalServiceNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.backoffice.AdditionalServiceTO;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingAdditionalServiceRP;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingRP;
import com.skybooking.skyflightservice.v1_0_0.service.implement.baseservice.BaseServiceIP;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.backoffice.AdditionalServiceSV;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.additionalservice.CreateAdditionalServiceRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.additionalservice.UpdateAdditionalServiceRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.backoffice.additionalservice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdditionalServiceIP extends BaseServiceIP implements AdditionalServiceSV {

    @Autowired private BookingRP bookingRP;
    @Autowired private BookingAdditionalServiceRP bookingAdditionalServiceRP;
    @Autowired private AdditionalServiceNQ additionalServiceNQ;

    @Override
    public StructureRS addService(CreateAdditionalServiceRQ request) {
        try {
            BookingEntity bookingEntity = bookingRP.getOne(request.getBookingId());

            if (bookingEntity.getStatus() == TicketConstant.TICKET_ISSUED
                || bookingEntity.getStatus() == TicketConstant.TICKET_ISSUED_MANUAL)
            {
                BookingAdditionalServiceEntity additionalServiceEntity = new BookingAdditionalServiceEntity();
                additionalServiceEntity.setCreatedBy(request.getEmployeeId());
                additionalServiceEntity.setBookingId(request.getBookingId());
                additionalServiceEntity.setServiceType(request.getService().getType());
                additionalServiceEntity.setServiceHappenedAt(request.getService().getHappenedAt());
                additionalServiceEntity.setServiceDescription(request.getService().getDescription());
                additionalServiceEntity.setSupplierName(request.getSupplier().getName());
                additionalServiceEntity.setSupplierFee(request.getSupplier().getFee());
                additionalServiceEntity.setSupplierDescription(request.getSupplier().getDescription());
                additionalServiceEntity.setCustomerFee(request.getCustomer().getFee());
                additionalServiceEntity.setCustomerDescription(request.getCustomer().getDescription());
                if (request.getBank() != null) {
                    additionalServiceEntity.setBankReceivedDate(request.getBank().getReceivedDate());
                    additionalServiceEntity.setBankFee(request.getBank().getFee());
                    additionalServiceEntity.setBankDescription(request.getBank().getDescription());
                }
                additionalServiceEntity.setStatus(1);
                bookingAdditionalServiceRP.save(additionalServiceEntity);

                bookingEntity.setIsAdditional(1);
                bookingRP.save(bookingEntity);

                return responseBody(HttpStatus.CREATED);
            }

            return responseBody(HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            return responseBody(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public StructureRS updateService(Integer id, UpdateAdditionalServiceRQ request) {

        try {
            BookingAdditionalServiceEntity additionalServiceEntity = bookingAdditionalServiceRP.getOne(id);
            if (additionalServiceEntity.getDeletedAt() != null)
                return responseBody(HttpStatus.BAD_REQUEST);

            additionalServiceEntity.setServiceType(request.getService().getType());
            additionalServiceEntity.setUpdatedBy(request.getEmployeeId());
            additionalServiceEntity.setServiceHappenedAt(request.getService().getHappenedAt());
            additionalServiceEntity.setServiceDescription(request.getService().getDescription());
            additionalServiceEntity.setCustomerFee(request.getCustomer().getFee());
            additionalServiceEntity.setCustomerDescription(request.getCustomer().getDescription());
            additionalServiceEntity.setSupplierName(request.getSupplier().getName());
            additionalServiceEntity.setSupplierFee(request.getSupplier().getFee());
            additionalServiceEntity.setSupplierDescription(request.getSupplier().getDescription());
            if (request.getBank() != null) {
                additionalServiceEntity.setBankReceivedDate(request.getBank().getReceivedDate());
                additionalServiceEntity.setBankFee(request.getBank().getFee());
                additionalServiceEntity.setBankDescription(request.getBank().getDescription());
            }
            additionalServiceEntity.setStatus(1);
            bookingAdditionalServiceRP.save(additionalServiceEntity);

            return responseBody(HttpStatus.OK);
        } catch (Exception exception) {
            return responseBody(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public StructureRS delete(Integer id) {
        try {
            BookingAdditionalServiceEntity entity = bookingAdditionalServiceRP.getOne(id);
            if (entity.getDeletedAt() != null)
                return responseBody(HttpStatus.BAD_REQUEST);

            entity.setDeletedAt(new Date());
            bookingAdditionalServiceRP.save(entity);
            List<AdditionalServiceTO> list = additionalServiceNQ.getAdditionalServices(entity.getBookingId());

            if (list.size() == 0) {
                BookingEntity bookingEntity = bookingRP.getOne(entity.getId());
                bookingEntity.setIsAdditional(0);
                bookingRP.save(bookingEntity);
            }

            return responseBody(HttpStatus.OK);
        } catch (Exception exception) {
            return responseBody(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public StructureRS listing(Integer bookingId) {

        List<AdditionalServiceRS> additionalServiceRSList = new ArrayList<>();
        List<AdditionalServiceTO> additionalServiceTOList = additionalServiceNQ.getAdditionalServices(bookingId);
        if (additionalServiceTOList.size() > 0) {
            additionalServiceTOList.forEach(item -> {
                AdditionalServiceRS additionalServiceRS = new AdditionalServiceRS();
                ServiceRS serviceRS = new ServiceRS();
                SupplierRS supplierRS = new SupplierRS();
                CustomerRS customerRS = new CustomerRS();
                BankRS bankRS = new BankRS();

                serviceRS.setType(item.getServiceType());
                serviceRS.setHappenedAt(item.getServiceHappenedAt());
                serviceRS.setDescription(item.getServiceDescription());

                supplierRS.setName(item.getSupplierName());
                supplierRS.setFee(item.getSupplierFee());
                supplierRS.setDescription(item.getSupplierDescription());

                customerRS.setFee(item.getCustomerFee());
                customerRS.setDescription(item.getCustomerDescription());

                bankRS.setReceivedDate(item.getBankReceivedDate());
                bankRS.setFee(item.getBankFee());
                bankRS.setDescription(item.getBankDescription());

                additionalServiceRS.setId(item.getId());
                additionalServiceRS.setBookingId(item.getBookingId());
                additionalServiceRS.setService(serviceRS);
                additionalServiceRS.setSupplier(supplierRS);
                additionalServiceRS.setCustomer(customerRS);
                additionalServiceRS.setBank(bankRS);
                additionalServiceRS.setCreatedBy(item.getCreatedBy());
                additionalServiceRS.setUpdatedBy(item.getUpdatedBy());
                additionalServiceRS.setCreatedAt(item.getCreatedAt());
                additionalServiceRS.setUpdatedAt(item.getUpdatedAt());
                additionalServiceRSList.add(additionalServiceRS);
            });

            return responseBodyWithSuccessMessage(additionalServiceRSList);
        } else {
            return responseBody(HttpStatus.NO_CONTENT);
        }
    }
}
