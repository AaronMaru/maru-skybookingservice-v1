package com.skybooking.staffservice.v1_0_0.service.interfaces.invitation;

import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.InviteStaffNoAccRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.SkyuserIdStaffRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.response.invitation.PendingEmailStaffRS;
import com.skybooking.staffservice.v1_0_0.ui.model.response.invitation.SkyuserDetailsRS;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InvitationSV {

    List<SkyuserDetailsRS> findSkyusers();

    void invSkyuser(SkyuserIdStaffRQ inviteStaff);

    void invSkyuserNotExistsAcc(InviteStaffNoAccRQ inviteStaffNoAccRQ);

    List<PendingEmailStaffRS> getPendingEmail();

    void removePendingEmail(Integer id);

    void resendPendingEmail(InviteStaffNoAccRQ inviteStaffNoAccRQ);

}
