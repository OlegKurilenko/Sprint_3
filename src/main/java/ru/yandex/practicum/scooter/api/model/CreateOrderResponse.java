package ru.yandex.practicum.scooter.api.model;

public class CreateOrderResponse {
    public Integer track;

    public CreateOrderResponse(Integer track) {
        this.track = track;
    }

    public Integer getTrack() {
        return track;
    }
}
