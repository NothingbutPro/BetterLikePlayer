package com.ics.likeplayer.Model;

public class DIrectories_Folders {
    String Name;
    String BaseAddress;
    String No_of_SOngs;

    public DIrectories_Folders(String name, String baseAddress, String no_of_SOngs) {
        this.Name = name;
        this.BaseAddress = baseAddress;
        this.No_of_SOngs = no_of_SOngs;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getBaseAddress() {
        return BaseAddress;
    }

    public void setBaseAddress(String baseAddress) {
        this.BaseAddress = baseAddress;
    }

    public String getNo_of_SOngs() {
        return No_of_SOngs;
    }

    public void setNo_of_SOngs(String no_of_SOngs) {
        this.No_of_SOngs = no_of_SOngs;
    }
}
