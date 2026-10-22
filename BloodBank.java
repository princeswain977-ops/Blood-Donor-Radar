public class BloodBank {
    private int bankId;
    private String bankName;
    private String address;
    private String city;
    private String contactNumber;

    public BloodBank() { }

    public BloodBank(String bankName, String address, String city, String contactNumber) {
        this.bankName = bankName;
        this.address = address;
        this.city = city;
        this.contactNumber = contactNumber;
    }

    public int getBankId() { return bankId; }
    public void setBankId(int bankId) { this.bankId = bankId; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    @Override
    public String toString() {
        return String.format("ID:%-4d %-28s | %-25s | %-12s | %s",
                bankId, bankName, address, city, contactNumber);
    }
}
