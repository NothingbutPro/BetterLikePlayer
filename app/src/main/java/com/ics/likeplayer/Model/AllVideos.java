package com.ics.likeplayer.Model;

public class AllVideos {
    String Name;
    String Length;
    String Thmb;


    String Path;

    public AllVideos(String name, String length, String thmb, String Path) {
        this.Name = name;
        this.Length = length;
        this.Thmb = thmb;
        this.Path = Path;
    }
    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this. Name = name;
    }

    public String getLength() {
        return Length;
    }

    public void setLength(String length) {
        this.Length = length;
    }

    public String getThmb() {
        return Thmb;
    }

    public void setThmb(String thmb) {
        this.Thmb = thmb;
    }
}
