package com.skybooking.staffservice.v1_0_0.ui.model.response.staff;

public class StaffTotalBookingRS {

    private Upcoming upcoming;
    private Completed completed;
    private Cancellation cancellation;

    public Upcoming getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(Upcoming upcoming) {
        this.upcoming = upcoming;
    }

    public Completed getCompleted() {
        return completed;
    }

    public void setCompleted(Completed completed) {
        this.completed = completed;
    }

    public Cancellation getCancellation() {
        return cancellation;
    }

    public void setCancellation(Cancellation cancellation) {
        this.cancellation = cancellation;
    }
}
