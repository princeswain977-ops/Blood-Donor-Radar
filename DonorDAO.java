import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the DONOR table.
 * Includes the "radar" search used to locate available donors by
 * blood group and/or city.
 */
public class DonorDAO {

    // ---------------------------------------------------------- CREATE
    public boolean addDonor(Donor d) {
        String sql = "INSERT INTO donor (full_name, blood_group, contact_number, email, city, "
                   + "last_donation_date, availability_status, bank_id) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, d.getFullName());
            ps.setString(2, d.getBloodGroup());
            ps.setString(3, d.getContactNumber());
            ps.setString(4, d.getEmail());
            ps.setString(5, d.getCity());
            ps.setDate(6, d.getLastDonationDate());
            ps.setString(7, d.getAvailabilityStatus());
            if (d.getBankId() != null) ps.setInt(8, d.getBankId());
            else ps.setNull(8, Types.INTEGER);

            int rows = ps.executeUpdate();
            System.out.println("Donor registered successfully. Rows affected: " + rows);
            return rows > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error: the Bank ID you entered does not exist -> " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database error while adding donor: " + e.getMessage());
        }
        return false;
    }

    // ------------------------------------------------------------ READ (all)
    public List<Donor> getAllDonors() {
        List<Donor> list = new ArrayList<>();
        String sql = "SELECT * FROM donor";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            System.out.println("Error fetching donors: " + e.getMessage());
        }
        return list;
    }

    // ------------------------------------------------------------ READ (radar search)
    /**
     * Finds available donors matching a blood group and/or city.
     * Pass null or "" for either parameter to ignore that filter.
     * This is the core "radar" lookup of the application.
     */
    public List<Donor> searchDonors(String bloodGroup, String city) {
        List<Donor> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT * FROM donor WHERE availability_status = 'Available'");

        if (bloodGroup != null && !bloodGroup.isEmpty()) sql.append(" AND blood_group = ?");
        if (city != null && !city.isEmpty()) sql.append(" AND city = ?");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int idx = 1;
            if (bloodGroup != null && !bloodGroup.isEmpty()) ps.setString(idx++, bloodGroup);
            if (city != null && !city.isEmpty()) ps.setString(idx++, city);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error searching donors: " + e.getMessage());
        }
        return list;
    }

    // ---------------------------------------------------------- UPDATE
    public boolean updateDonor(Donor d) {
        String sql = "UPDATE donor SET full_name=?, blood_group=?, contact_number=?, email=?, city=?, "
                   + "last_donation_date=?, availability_status=?, bank_id=? WHERE donor_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, d.getFullName());
            ps.setString(2, d.getBloodGroup());
            ps.setString(3, d.getContactNumber());
            ps.setString(4, d.getEmail());
            ps.setString(5, d.getCity());
            ps.setDate(6, d.getLastDonationDate());
            ps.setString(7, d.getAvailabilityStatus());
            if (d.getBankId() != null) ps.setInt(8, d.getBankId());
            else ps.setNull(8, Types.INTEGER);
            ps.setInt(9, d.getDonorId());

            int rows = ps.executeUpdate();
            if (rows == 0) System.out.println("No donor found with ID " + d.getDonorId());
            else System.out.println("Donor updated successfully.");
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error updating donor: " + e.getMessage());
            return false;
        }
    }

    // ---------------------------------------------------------- DELETE
    public boolean deleteDonor(int donorId) {
        String sql = "DELETE FROM donor WHERE donor_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, donorId);
            int rows = ps.executeUpdate();
            if (rows == 0) System.out.println("No donor found with ID " + donorId);
            else System.out.println("Donor deleted successfully.");
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting donor: " + e.getMessage());
            return false;
        }
    }

    // ------------------------------------------------------------ helper
    private Donor mapRow(ResultSet rs) throws SQLException {
        Donor d = new Donor();
        d.setDonorId(rs.getInt("donor_id"));
        d.setFullName(rs.getString("full_name"));
        d.setBloodGroup(rs.getString("blood_group"));
        d.setContactNumber(rs.getString("contact_number"));
        d.setEmail(rs.getString("email"));
        d.setCity(rs.getString("city"));
        d.setLastDonationDate(rs.getDate("last_donation_date"));
        d.setAvailabilityStatus(rs.getString("availability_status"));
        int bankId = rs.getInt("bank_id");
        d.setBankId(rs.wasNull() ? null : bankId);
        return d;
    }
}
