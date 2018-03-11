package ersel.greatbit.net.ersel.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Eslam on 3/8/2018.
 */

public class Shipment implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("trackNumber")
    @Expose
    private String trackNumber;
    @SerializedName("shippingCost")
    @Expose
    private Integer shippingCost;
    @SerializedName("totalPrice")
    @Expose
    private Integer totalPrice;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("clientId")
    @Expose
    private Integer clientId;
    @SerializedName("employeeId")
    @Expose
    private Integer employeeId;
    @SerializedName("recipientId")
    @Expose
    private Integer recipientId;
    @SerializedName("recipientAddressId")
    @Expose
    private Integer recipientAddressId;
    @SerializedName("clientName")
    @Expose
    private String clientName;
    @SerializedName("clientEmail")
    @Expose
    private String clientEmail;
    @SerializedName("recipientName")
    @Expose
    private String recipientName;
    @SerializedName("employeeName")
    @Expose
    private String employeeName;
    @SerializedName("employeeMobile")
    @Expose
    private String employeeMobile;
    @SerializedName("recipientAddressText")
    @Expose
    private String recipientAddressText;
    @SerializedName("recipientAddressCountryId")
    @Expose
    private Integer recipientAddressCountryId;
    @SerializedName("recipientAddressCityId")
    @Expose
    private Integer recipientAddressCityId;
    @SerializedName("recipientAddressAreaId")
    @Expose
    private Integer recipientAddressAreaId;
    @SerializedName("recipientAddressLatitude")
    @Expose
    private Double recipientAddressLatitude;
    @SerializedName("recipientAddressLongitude")
    @Expose
    private Double recipientAddressLongitude;
    @SerializedName("recipientMobile")
    @Expose
    private String recipientMobile;
    @SerializedName("lastShipmentStatus")
    @Expose
    private Integer lastShipmentStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    public Integer getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Integer shippingCost) {
        this.shippingCost = shippingCost;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Integer recipientId) {
        this.recipientId = recipientId;
    }

    public Integer getRecipientAddressId() {
        return recipientAddressId;
    }

    public void setRecipientAddressId(Integer recipientAddressId) {
        this.recipientAddressId = recipientAddressId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeMobile() {
        return employeeMobile;
    }

    public void setEmployeeMobile(String employeeMobile) {
        this.employeeMobile = employeeMobile;
    }

    public String getRecipientAddressText() {
        return recipientAddressText;
    }

    public void setRecipientAddressText(String recipientAddressText) {
        this.recipientAddressText = recipientAddressText;
    }

    public Integer getRecipientAddressCountryId() {
        return recipientAddressCountryId;
    }

    public void setRecipientAddressCountryId(Integer recipientAddressCountryId) {
        this.recipientAddressCountryId = recipientAddressCountryId;
    }

    public Integer getRecipientAddressCityId() {
        return recipientAddressCityId;
    }

    public void setRecipientAddressCityId(Integer recipientAddressCityId) {
        this.recipientAddressCityId = recipientAddressCityId;
    }

    public Integer getRecipientAddressAreaId() {
        return recipientAddressAreaId;
    }

    public void setRecipientAddressAreaId(Integer recipientAddressAreaId) {
        this.recipientAddressAreaId = recipientAddressAreaId;
    }

    public Double getRecipientAddressLatitude() {
        return recipientAddressLatitude;
    }

    public void setRecipientAddressLatitude(Double recipientAddressLatitude) {
        this.recipientAddressLatitude = recipientAddressLatitude;
    }

    public Double getRecipientAddressLongitude() {
        return recipientAddressLongitude;
    }

    public void setRecipientAddressLongitude(Double recipientAddressLongitude) {
        this.recipientAddressLongitude = recipientAddressLongitude;
    }

    public String getRecipientMobile() {
        return recipientMobile;
    }

    public void setRecipientMobile(String recipientMobile) {
        this.recipientMobile = recipientMobile;
    }

    public Integer getLastShipmentStatus() {
        return lastShipmentStatus;
    }

    public void setLastShipmentStatus(Integer lastShipmentStatus) {
        this.lastShipmentStatus = lastShipmentStatus;
    }

}