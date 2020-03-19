package com.skybooking.paymentservice.v1_0_0.service.implement.activity;

import com.skybooking.paymentservice.v1_0_0.io.enitity.activity.ActivityEntity;
import com.skybooking.paymentservice.v1_0_0.io.repository.activity.ActivityRP;
import com.skybooking.paymentservice.v1_0_0.service.interfaces.activity.ActivitySV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityIP implements ActivitySV {

    @Autowired
    private ActivityRP activityRP;

    private ActivityEntity activityEntity;

    public ActivityIP() {
        activityEntity = new ActivityEntity();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * set the log name
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param name
     */
    @Override
    public void logName(String name) {
        activityEntity.setLogName(name);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * set the the user that do the action
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id   user's id
     * @param type user
     */
    @Override
    public void causedBy(Long id, String type) {
        activityEntity.setCauserId(id);
        activityEntity.setCauserType(type);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * set the subject that the user performed any action.
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @param type
     */
    @Override
    public void performedOn(Integer id, String type) {
        activityEntity.setSubjectId(id);
        activityEntity.setSubjectType(type);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * set the log message and save to database
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param message
     */
    @Override
    public void log(String message) {
        activityEntity.setDescription(message);
        this.save(activityEntity);
        activityEntity = new ActivityEntity();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * save the activity log to database
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param activityEntity
     */
    private void save(ActivityEntity activityEntity) {

        ActivityEntity entity = new ActivityEntity();
        entity.setLogName(activityEntity.getLogName());
        entity.setDescription(activityEntity.getDescription());
        entity.setCauserType(activityEntity.getCauserType());
        entity.setCauserId(activityEntity.getCauserId());
        entity.setSubjectType(activityEntity.getSubjectType());
        entity.setSubjectId(activityEntity.getSubjectId());

        activityRP.save(entity);
    }

}
