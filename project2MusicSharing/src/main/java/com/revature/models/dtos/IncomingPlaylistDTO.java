package com.revature.models.dtos;

public class IncomingPlaylistDTO {
    private String playlistName;
    private boolean isPublic;
    private int userId;

    public IncomingPlaylistDTO() {
    }

    public IncomingPlaylistDTO(String playlistName, boolean isPublic, int userId) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
