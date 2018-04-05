package ersel.greatbit.net.ersel.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Eslam on 3/8/2018.
 */

public class Shipment {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("trackNumber")
    @Expose
    private String trackNumber;
    @SerializedName("cost")
    @Expose
    private double cost;
    @SerializedName("moneyCollection")
    @Expose
    private double moneyCollection;
    @SerializedName("employeeCommision")
    @Expose
    private Integer employeeCommision;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("clientId")
    @Expose
    private Integer clientId;
    @SerializedName("clientAddressId")
    @Expose
    private Integer clientAddressId;
    @SerializedName("clientMobileId")
    @Expose
    private Integer clientMobileId;
    @SerializedName("clientName")
    @Expose
    private String clientName;
    @SerializedName("clientEmail")
    @Expose
    private String clientEmail;
    @SerializedName("clientAddress")
    @Expose
    private String clientAddress;
    @SerializedName("clientCountryId")
    @Expose
    private Integer clientCountryId;
    @SerializedName("clientCityId")
    @Expose
    private Integer clientCityId;
    @SerializedName("clientAreaId")
    @Expose
    private Integer clientAreaId;
    @SerializedName("clientAddressLatitude")
    @Expose
    private double clientAddressLatitude;
    @SerializedName("clientAddressLongitude")
    @Expose
    private double clientAddressLongitude;
    @SerializedName("employeeId")
    @Expose
    private Integer employeeId;
    @SerializedName("employeeName")
    @Expose
    private String employeeName;
    @SerializedName("employeeMobile")
    @Expose
    private String employeeMobile;
    @SerializedName("recipientId")
    @Expose
    private Integer recipientId;
    @SerializedName("recipientAddressId")
    @Expose
    private Integer recipientAddressId;
    @SerializedName("recipientName")
    @Expose
    private String recipientName;
    @SerializedName("recipientMobile")
    @Expose
    private String recipientMobile;
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
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("addressText")
    @Expose
    private String addressText;
    @SerializedName("addressCountryId")
    @Expose
    private Integer addressCountryId;
    @SerializedName("addressCountryName")
    @Expose
    private String addressCountryName;
    @SerializedName("addressCityId")
    @Expose
    private Integer addressCityId;
    @SerializedName("addressCityName")
    @Expose
    private String addressCityName;
    @SerializedName("addressAreaId")
    @Expose
    private Integer addressAreaId;
    @SerializedName("addressAreaName")
    @Expose
    private String addressAreaName;
    @SerializedName("addressLatitude")
    @Expose
    private Double addressLatitude;
    @SerializedName("addressLongitude")
    @Expose
    private Double addressLongitude;
    @SerializedName("lastStatus")
    @Expose
    private Integer lastStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getMoneyCollection() {
        return moneyCollection;
    }

    public void setMoneyCollection(double moneyCollection) {
        this.moneyCollection = moneyCollection;
    }

    public Integer getEmployeeCommision() {
        return employeeCommision;
    }

    public void setEmployeeCommision(Integer employeeCommision) {
        this.employeeCommision = employeeCommision;
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

    public Integer getClientAddressId() {
        return clientAddressId;
    }

    public void setClientAddressId(Integer clientAddressId) {
        this.clientAddressId = clientAddressId;
    }

    public Integer getClientMobileId() {
        return clientMobileId;
    }

    public void setClientMobileId(Integer clientMobileId) {
        this.clientMobileId = clientMobileId;
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

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public Integer getClientCountryId() {
        return clientCountryId;
    }

    public void setClientCountryId(Integer clientCountryId) {
        this.clientCountryId = clientCountryId;
    }

    public Integer getClientCityId() {
        return clientCityId;
    }

    public void setClientCityId(Integer clientCityId) {
        this.clientCityId = clientCityId;
    }

    public Integer getClientAreaId() {
        return clientAreaId;
    }

    public void setClientAreaId(Integer clientAreaId) {
        this.clientAreaId = clientAreaId;
    }

    public double getClientAddressLatitude() {
        return clientAddressLatitude;
    }

    public void setClientAddressLatitude(double clientAddressLatitude) {
        this.clientAddressLatitude = clientAddressLatitude;
    }

    public double getClientAddressLongitude() {
        return clientAddressLongitude;
    }

    public void setClientAddressLongitude(double clientAddressLongitude) {
        this.clientAddressLongitude = clientAddressLongitude;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
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

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientMobile() {
        return recipientMobile;
    }

    public void setRecipientMobile(String recipientMobile) {
        this.recipientMobile = recipientMobile;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddressText() {
        return addressText;
    }

    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }

    public Integer getAddressCountryId() {
        return addressCountryId;
    }

    public void setAddressCountryId(Integer addressCountryId) {
        this.addressCountryId = addressCountryId;
    }

    public String getAddressCountryName() {
        return addressCountryName;
    }

    public void setAddressCountryName(String addressCountryName) {
        this.addressCountryName = addressCountryName;
    }

    public Integer getAddressCityId() {
        return addressCityId;
    }

    public void setAddressCityId(Integer addressCityId) {
        this.addressCityId = addressCityId;
    }

    public String getAddressCityName() {
        return addressCityName;
    }

    public void setAddressCityName(String addressCityName) {
        this.addressCityName = addressCityName;
    }

    public Integer getAddressAreaId() {
        return addressAreaId;
    }

    public void setAddressAreaId(Integer addressAreaId) {
        this.addressAreaId = addressAreaId;
    }

    public String getAddressAreaName() {
        return addressAreaName;
    }

    public void setAddressAreaName(String addressAreaName) {
        this.addressAreaName = addressAreaName;
    }

    public Double getAddressLatitude() {
        return addressLatitude;
    }

    public void setAddressLatitude(Double addressLatitude) {
        this.addressLatitude = addressLatitude;
    }

    public Double getAddressLongitude() {
        return addressLongitude;
    }

    public void setAddressLongitude(Double addressLongitude) {
        this.addressLongitude = addressLongitude;
    }

    public Integer getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(Integer lastStatus) {
        this.lastStatus = lastStatus;
    }

}