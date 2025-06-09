package model;

import java.io.Serializable;
import tools.Utils;

public class KOL implements Serializable {
    private String kolId;
    private String name;
    private String phoneNumber;
    private String email;
    private String platformCode;
    private long followerCount;
    private int commissionRate;

    public KOL() {}

    public KOL(String kolId, String name, String phoneNumber, String email, 
               String platformCode, long followerCount) {
        this.kolId = kolId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.platformCode = platformCode;
        this.followerCount = followerCount;
        this.commissionRate = calculateCommissionRate(followerCount);
    }

    private int calculateCommissionRate(long followers) {
        return followers > 1000000 ? 25 : 20;
    }

    public String getKolId() { return kolId; }
    public void setKolId(String kolId) { this.kolId = kolId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPlatformCode() { return platformCode; }
    public void setPlatformCode(String platformCode) { this.platformCode = platformCode; }

    public long getFollowerCount() { return followerCount; }
    public void setFollowerCount(long followerCount) { 
        this.followerCount = followerCount;
        this.commissionRate = calculateCommissionRate(followerCount);
    }

    public int getCommissionRate() { return commissionRate; }

    public String getCategoryCode() {
        return kolId.substring(0, 2);
    }

    @Override
    public String toString() {
        String platformName = Utils.getPlatformName(platformCode);
        return String.format("%-10s | %-20s | %-12s | %-15s | %,10d | %d%%",
                kolId, name, phoneNumber, platformName, followerCount, commissionRate);
    }
}