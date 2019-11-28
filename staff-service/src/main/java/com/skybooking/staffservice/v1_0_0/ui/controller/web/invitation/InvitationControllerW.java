package com.skybooking.staffservice.v1_0_0.ui.controller.web.invitation;


import com.skybooking.staffservice.v1_0_0.service.interfaces.invitation.InvitationSV;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.InviteStaffNoAccRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.SkyuserIdStaffRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.response.invitation.PendingEmailStaffRS;
import com.skybooking.staffservice.v1_0_0.ui.model.response.invitation.SkyuserDetailsRS;
import com.skybooking.staffservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/wv1.0.0")
public class InvitationControllerW {

    @Autowired
    private InvitationSV invitationSV;

    @Autowired
    private Localization localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Find skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/find-skyuser")
    public ResponseEntity findSkyuser() {
        List<SkyuserDetailsRS> skyusers =  invitationSV.findSkyusers();
        return new ResponseEntity<>(skyusers, HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Invite skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @PostMapping("/invite-skyuser")
    public ResponseEntity invSkyuser(@Valid @RequestBody SkyuserIdStaffRQ inviteRQ) {
        invitationSV.invSkyuser(inviteRQ);
        return new ResponseEntity<>(localization.resAPI("inv_succ", ""), HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Invite skyuser no account
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @PostMapping("/invite-skyuser-no-acc")
    public ResponseEntity invSkyuserNoAcc(@Valid @RequestBody InviteStaffNoAccRQ inviteStaffNoAccRQ) {
        invitationSV.invSkyuserNotExistsAcc(inviteStaffNoAccRQ);
        return new ResponseEntity<>(localization.resAPI("inv_succ", ""), HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Listing pending email staff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/list-pending-email")
    public ResponseEntity getPendingEmail() {
        List<PendingEmailStaffRS> emails = invitationSV.getPendingEmail();
        return new ResponseEntity<>(emails, HttpStatus.OK);
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
    public ResponseEntity deletePendingEmail(@PathVariable Integer id) {
        invitationSV.removePendingEmail(id);
        return new ResponseEntity<>(localization.resAPI("del_succ", ""), HttpStatus.OK);
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
    public ResponseEntity resendPendingEmail(@RequestBody InviteStaffNoAccRQ inviteStaffNoAccRQ) {
        invitationSV.resendPendingEmail(inviteStaffNoAccRQ);
        return new ResponseEntity<>(localization.resAPI("Resend", ""), HttpStatus.OK);
    }


}
