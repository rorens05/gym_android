package com.example.gym.models;

public class HealthStatus {
    public String id;
    public String day_id;
    public String height;
    public String weight;
    public String blood_pressure;
    public String sugar;
    public String waist_line;
    public String created_at;
    public String updated_at;
    public String image;

    public HealthStatus(String id, String day_id, String height, String weight, String blood_pressure, String sugar, String waist_line, String created_at, String updated_at, String image) {
        this.id = id;
        this.day_id = day_id;
        this.height = height;
        this.weight = weight;
        this.blood_pressure = blood_pressure;
        this.sugar = sugar;
        this.waist_line = waist_line;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.image = image;
    }

    @Override
    public String toString() {
        return "HealthStatus{" +
                "id='" + id + '\'' +
                ", day_id='" + day_id + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", blood_pressure='" + blood_pressure + '\'' +
                ", sugar='" + sugar + '\'' +
                ", waist_line='" + waist_line + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
