import java.sql.Date;

/**
 * Model class mapping to the DONATION_RECORD table.
 * Links a Donor to a DonationCamp.
 */
public class Donation {
    private int donationId;
    private int donorId;
    private Integer campId; // nullable -> foreign key to DONATION_CAMP
    private Date donationDate;
    private int unitsDonated;

    public Donation() { }

    public Donation(int donorId, Integer campId, Date donationDate, int unitsDonated) {
        this.donorId = donorId;
        this.campId = campId;
        this.donationDate = donationDate;
        this.unitsDonated = unitsDonated;
    }

    public int getDonationId() { return donationId; }
    public void setDonationId(int donationId) { this.donationId = donationId; }

    public int getDonorId() { return donorId; }
    public void setDonorId(int donorId) { this.donorId = donorId; }

    public Integer getCampId() { return campId; }
    public void setCampId(Integer campId) { this.campId = campId; }

    public Date getDonationDate() { return donationDate; }
    public void setDonationDate(Date donationDate) { this.donationDate = donationDate; }

    public int getUnitsDonated() { return unitsDonated; }
    public void setUnitsDonated(int unitsDonated) { this.unitsDonated = unitsDonated; }

    @Override
    public String toString() {
        return String.format("ID:%-4d DonorID:%-6d CampID:%-6s Date:%-12s Units:%d",
                donationId, donorId, (campId == null ? "-" : campId), donationDate, unitsDonated);
    }
}
