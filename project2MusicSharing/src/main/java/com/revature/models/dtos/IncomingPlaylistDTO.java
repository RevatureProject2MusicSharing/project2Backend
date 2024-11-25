package com.revature.models.dtos;

import java.util.UUID;

public class IncomingPlaylistDTO {
    private String playlistName;
    private boolean isPublic;
    private UUID userId;

    public IncomingPlaylistDTO() {
        isPublic = false;
    }

    public IncomingPlaylistDTO(String playlistName, boolean isPublic, UUID userId) {
        this.playlistName = playlistName;
        this.isPublic = isPublic;
        this.userId = userId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "IncomingPlaylistDTO{" +
                "playlistName='" + playlistName + '\'' +
                ", isPublic=" + isPublic +
                ", userId=" + userId +
                '}';
    }
}
