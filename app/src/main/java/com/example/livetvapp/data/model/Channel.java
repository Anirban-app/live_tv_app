package com.example.livetvapp.data.model;

public class Channel {
    private String id;
    private String name;
    private String logoUrl;
    private String streamUrl;

    public Channel(String id, String name, String logoUrl, String streamUrl) {
        this.id = id;
        this.name = name;
        this.logoUrl = logoUrl;
        this.streamUrl = streamUrl;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getLogoUrl() { return logoUrl; }
    public String getStreamUrl() { return streamUrl; }
}
