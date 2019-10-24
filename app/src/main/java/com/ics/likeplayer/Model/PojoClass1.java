package com.ics.likeplayer.Model;

/**
 * Created by kuldeep on 13/02/18.
 */

public class PojoClass1 {

    private String songsinger_name,song_type;

    public String getSongsinger_name() {
        return songsinger_name;
    }

    public void setSongsinger_name(String songsinger_name) {
        this.songsinger_name = songsinger_name;
    }

    public String getSong_type() {
        return song_type;
    }

    public void setSong_type(String song_type) {
        this.song_type = song_type;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    private int logo;
}
