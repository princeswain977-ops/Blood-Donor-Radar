import java.sql.Date;

/**
 * Model class mapping to the DONOR table.
 */
public class Donor {
    private int donorId;
    private String fullName;
    private String bloodGroup;
    private String contactNumber;
    private String email;
    private String city;
    private Date lastDonationDate;
    private String availabilityStatus;
    private Integer bankId; // nullable -> foreign key to BLOOD_BANK

    public Donor() { }

    public Donor(String fullName, String bloodGroup, String contactNumber, String email,
                 String city, Date lastDonationDate, String availabilityStatus, Integer bankId) {
        this.fullName = fullName;
        this.bloodGroup = bloodGroup;
        this.contactNumber = contactNumber;
        this.email = email;
        this.city = city;
        this.lastDonationDate = lastDonationDate;
        this.availabilityStatus = availabilityStatus;
        this.bankId = bankId;
    }

    public int getDonorId() { return donorId; }
    public void setDonorId(int donorId) { this.donorId = donorId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Date getLastDonationDate() { return lastDonationDate; }
    public void setLastDonationDate(Date lastDonationDate) { this.lastDonationDate = lastDonationDate; }

    public String getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(String availabilityStatus) { this.availabilityStatus = availabilityStatus; }

    public Integer getBankId() { return bankId; }
    public void setBankId(Integer bankId) { this.bankId = bankId; }

    @Override
    public String toString() {
        return String.format("ID:%-4d %-15s | %-3s | %-11s | %-20s | %-10s | %-13s | BankID:%s",
                donorId, fullName, bloodGroup, contactNumber, email, city,
                availabilityStatus, (bankId == null ? "-" : bankId));
    }
}
