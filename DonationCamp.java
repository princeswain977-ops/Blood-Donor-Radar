import java.sql.Date;

/**
 * Model class mapping to the DONATION_CAMP table.
 */
public class DonationCamp {
    private int campId;
    private String campName;
    private String location;
    private Date campDate;
    private Integer bankId; // nullable -> foreign key to BLOOD_BANK

    public DonationCamp() { }

    public DonationCamp(String campName, String location, Date campDate, Integer bankId) {
        this.campName = campName;
        this.location = location;
        this.campDate = campDate;
        this.bankId = bankId;
    }

    public int getCampId() { return campId; }
    public void setCampId(int campId) { this.campId = campId; }

    public String getCampName() { return campName; }
    public void setCampName(String campName) { this.campName = campName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Date getCampDate() { return campDate; }
    public void setCampDate(Date campDate) { this.campDate = campDate; }

    public Integer getBankId() { return bankId; }
    public void setBankId(Integer bankId) { this.bankId = bankId; }

    @Override
    public String toString() {
        return String.format("ID:%-4d %-28s | %-20s | %-10s | BankID:%s",
                campId, campName, location, campDate, (bankId == null ? "-" : bankId));
    }
}
