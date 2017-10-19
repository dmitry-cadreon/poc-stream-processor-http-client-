package com.example.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Builder
public class Campaign {

    @JsonProperty("start.date")
    private Date date;
    @JsonProperty("entity.a")
    public EntityA entityA;
    @JsonProperty("entity.b")
    public EntityB entityB;

    public String getDate() {
        if(this.date != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.format(this.date);
        }
        return null;
    }

}
