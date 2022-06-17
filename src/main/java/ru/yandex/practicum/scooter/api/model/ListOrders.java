package ru.yandex.practicum.scooter.api.model;

import java.util.ArrayList;
import java.util.Date;

public class ListOrders {
    private Integer id;
    private Object courierId;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private Integer rentTime;
    private Date deliveryDate;
    private Integer track;
    private ArrayList<String> color;
    private String comment;
    private Date createdAt;
    private Date updatedAt;
    private Integer status;

    public ListOrders(Integer id, Object courierId, String firstName, String lastName, String address, String metroStation, String phone, Integer rentTime, Date deliveryDate, Integer track, ArrayList<String> color, String comment, Date createdAt, Date updatedAt, Integer status) {
        this.id = id;
        this.courierId = courierId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.track = track;
        this.color = color;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Object getCourierId() {
        return courierId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getRentTime() {
        return rentTime;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public Integer getTrack() {
        return track;
    }

    public ArrayList<String> getColor() {
        return color;
    }

    public String getComment() {
        return comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Integer getStatus() {
        return status;
    }
}
