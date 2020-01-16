package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking;

public class BookingBaggageInfoTQ {

    private int pieceStatus;
    private String passType;
    private String bagAirline;
    private Integer bagPiece;
    private Integer bagWeight;
    private String bagUnit;

    public int getPieceStatus() {
        return pieceStatus;
    }

    public void setPieceStatus(int pieceStatus) {
        this.pieceStatus = pieceStatus;
    }

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public String getBagAirline() {
        return bagAirline;
    }

    public void setBagAirline(String bagAirline) {
        this.bagAirline = bagAirline;
    }

    public Integer getBagPiece() {
        return bagPiece;
    }

    public void setBagPiece(Integer bagPiece) {
        this.bagPiece = bagPiece;
    }

    public Integer getBagWeight() {
        return bagWeight;
    }

    public void setBagWeight(Integer bagWeight) {
        this.bagWeight = bagWeight;
    }

    public String getBagUnit() {
        return bagUnit;
    }

    public void setBagUnit(String bagUnit) {
        this.bagUnit = bagUnit;
    }

}
