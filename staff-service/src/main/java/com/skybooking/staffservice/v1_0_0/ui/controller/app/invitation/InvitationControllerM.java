package com.skybooking.staffservice.v1_0_0.ui.controller.app.invitation;

import com.skybooking.staffservice.v1_0_0.service.interfaces.invitation.InvitationSV;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.InviteStaffNoAccRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.SkyuserIdStaffRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.staffservice.v1_0_0.ui.model.response.invitation.PendingEmailStaffRS;
import com.skybooking.staffservice.v1_0_0.ui.model.response.invitation.SkyuserDetailsRS;
import com.skybooking.staffservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/mv1.0.0")
public class InvitationControllerM {


    @Autowired
    private InvitationSV invitationSV;

    @Autowired
    private LocalizationBean localization;



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Find skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/find-skyuser")
    public ResRS findSkyuser() {
        List<SkyuserDetailsRS> skyusers =  invitationSV.findSkyUsers();
        return localization.resAPI(HttpStatus.OK,"vf_succ", skyusers);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Invite skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @PostMapping("/invite-skyuser")
    public ResRS invSkyuser(@Valid @RequestBody SkyuserIdStaffRQ inviteRQ) {
        invitationSV.inviteSkyUser(inviteRQ);
        return localization.resAPI(HttpStatus.OK,"inv_succ", "");
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Invite skyuser no account
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @PostMapping("/invite-skyuser-no-acc")
    public ResRS invSkyuserNoAcc(@Valid @RequestBody InviteStaffNoAccRQ inviteStaffNoAccRQ) {
        invitationSV.inviteSkyUserNotExistsAcc(inviteStaffNoAccRQ);
        return localization.resAPI(HttpStatus.OK,"inv_succ", "");
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Listing pending email staff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/list-pending-email")
    public ResRS getPendingEmail() {
        List<PendingEmailStaffRS> emails = invitationSV.getPendingEmail();
        return localization.resAPI(HttpStatus.OK,"res_succ", emails);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Delete pending email staff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param {id}
     * @Return ResponseEntity
     */
    @DeleteMapping("/list-pending-email/{id}")
    public ResRS deletePendingEmail(@PathVariable Integer id) {
        invitationSV.removePendingEmail(id);
        return localization.resAPI(HttpStatus.OK,"del_succ", "");
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Resend pending email staff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param inviteStaffNoAccRQ
     * @Return ResponseEntity
     */
    @PostMapping("/list-pending-email/resend")
    public ResRS resendPendingEmail(@RequestBody InviteStaffNoAccRQ inviteStaffNoAccRQ) {
        invitationSV.resendPendingEmail(inviteStaffNoAccRQ);
        return localization.resAPI(HttpStatus.OK,"sent_succ", "");
    }


}
