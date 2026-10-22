import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the DONATION_RECORD table.
 * Each record links a Donor to a DonationCamp (both foreign keys).
 */
public class DonationDAO {

    // ---------------------------------------------------------- CREATE
    public boolean addDonation(Donation d) {
        String sql = "INSERT INTO donation_record (donor_id, camp_id, donation_date, units_donated) VALUES (?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, d.getDonorId());
            if (d.getCampId() != null) ps.setInt(2, d.getCampId());
            else ps.setNull(2, Types.INTEGER);
            ps.setDate(3, d.getDonationDate());
            ps.setInt(4, d.getUnitsDonated());

            int rows = ps.executeUpdate();
            System.out.println("Donation recorded successfully. Rows affected: " + rows);
            return rows > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error: the Donor ID or Camp ID you entered does not exist -> " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database error while recording donation: " + e.getMessage());
        }
        return false;
    }

    // ------------------------------------------------------------ READ
    public List<Donation> getAllDonations() {
        List<Donation> list = new ArrayList<>();
        String sql = "SELECT * FROM donation_record";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Donation d = new Donation();
                d.setDonationId(rs.getInt("donation_id"));
                d.setDonorId(rs.getInt("donor_id"));
                int campId = rs.getInt("camp_id");
                d.setCampId(rs.wasNull() ? null : campId);
                d.setDonationDate(rs.getDate("donation_date"));
                d.setUnitsDonated(rs.getInt("units_donated"));
                list.add(d);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching donation records: " + e.getMessage());
        }
        return list;
    }

    // ---------------------------------------------------------- UPDATE
    public boolean updateDonation(Donation d) {
        String sql = "UPDATE donation_record SET donor_id=?, camp_id=?, donation_date=?, units_donated=? WHERE donation_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, d.getDonorId());
            if (d.getCampId() != null) ps.setInt(2, d.getCampId());
            else ps.setNull(2, Types.INTEGER);
            ps.setDate(3, d.getDonationDate());
            ps.setInt(4, d.getUnitsDonated());
            ps.setInt(5, d.getDonationId());

            int rows = ps.executeUpdate();
            if (rows == 0) System.out.println("No donation record found with ID " + d.getDonationId());
            else System.out.println("Donation record updated successfully.");
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error updating donation record: " + e.getMessage());
            return false;
        }
    }

    // ---------------------------------------------------------- DELETE
    public boolean deleteDonation(int donationId) {
        String sql = "DELETE FROM donation_record WHERE donation_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, donationId);
            int rows = ps.executeUpdate();
            if (rows == 0) System.out.println("No donation record found with ID " + donationId);
            else System.out.println("Donation record deleted successfully.");
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting donation record: " + e.getMessage());
            return false;
        }
    }
}
