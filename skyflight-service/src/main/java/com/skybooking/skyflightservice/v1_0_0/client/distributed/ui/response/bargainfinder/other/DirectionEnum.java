package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.other;

import java.io.Serializable;

public enum DirectionEnum implements Serializable {
    AF("AF"),
    AL("AL"),
    AP("AP"),
    AT("AT"),
    CA("CA"),
    CT("CT"),
    DI("DI"),
    DO("DO"),
    DU("DU"),
    EH("EH"),
    EM("EM"),
    EU("EU"),
    FE("FE"),
    IN("IN"),
    ME("ME"),
    NA("NA"),
    NP("NP"),
    PA("PA"),
    PE("PE"),
    PN("PN"),
    PO("PO"),
    PV("PV"),
    RU("RU"),
    RW("RW"),
    SA("SA"),
    SN("SN"),
    SP("SP"),
    TB("TB"),
    TS("TS"),
    TT("TT"),
    US("US"),
    WH("WH"),
    XX("XX");

    private String value;

    DirectionEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
