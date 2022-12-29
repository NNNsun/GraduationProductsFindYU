package com.example.navermapex_2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MazeResult {
    //DTO
    @SerializedName("route")
    @Expose
    private String route;

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return route;
    }
}
