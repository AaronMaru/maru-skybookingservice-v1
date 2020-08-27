package com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking;

public class ShareFlightRQ {

    private String request;
    private String itinerary;
    private String emailFrom;
    private String emailsTo;
    private String description;
    private String link;

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailsTo() {
        return emailsTo;
    }

    public void setEmailsTo(String emailsTo) {
        this.emailsTo = emailsTo;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
