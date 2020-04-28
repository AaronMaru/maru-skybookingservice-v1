package com.skybooking.staffservice.v1_0_0.service.interfaces.invitation;

import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.InvitationExpireRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.InviteStaffNoAccRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.ResendPendingEmailRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.SkyuserIdStaffRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.response.invitation.PendingEmailStaffRS;
import com.skybooking.staffservice.v1_0_0.ui.model.response.invitation.SkyuserDetailsRS;

import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

@Service
public interface InvitationSV {

    List<SkyuserDetailsRS> findSkyUsers();

    void inviteSkyUser(SkyuserIdStaffRQ inviteStaff);

    void inviteSkyUserNotExistsAcc(InviteStaffNoAccRQ inviteStaffNoAccRQ) throws UnsupportedEncodingException;

    List<PendingEmailStaffRS> getPendingEmail();

    void removePendingEmail(Integer id);

    void resendPendingEmail(ResendPendingEmailRQ resendPendingEmail) throws UnsupportedEncodingException;

    HashMap<String, Boolean> checkExpired(InvitationExpireRQ invitationExpireRQ) throws UnsupportedEncodingException;

}
