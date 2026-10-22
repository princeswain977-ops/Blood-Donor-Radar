import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the DONATION_CAMP table.
 */
public class DonationCampDAO {

    // ---------------------------------------------------------- CREATE
    public boolean addCamp(DonationCamp c) {
        String sql = "INSERT INTO donation_camp (camp_name, location, camp_date, bank_id) VALUES (?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getCampName());
            ps.setString(2, c.getLocation());
            ps.setDate(3, c.getCampDate());
            if (c.getBankId() != null) ps.setInt(4, c.getBankId());
            else ps.setNull(4, Types.INTEGER);

            int rows = ps.executeUpdate();
            System.out.println("Donation camp added successfully. Rows affected: " + rows);
            return rows > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error: the Bank ID you entered does not exist -> " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database error while adding camp: " + e.getMessage());
        }
        return false;
    }

    // ------------------------------------------------------------ READ
    public List<DonationCamp> getAllCamps() {
        List<DonationCamp> list = new ArrayList<>();
        String sql = "SELECT * FROM donation_camp";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                DonationCamp c = new DonationCamp();
                c.setCampId(rs.getInt("camp_id"));
                c.setCampName(rs.getString("camp_name"));
                c.setLocation(rs.getString("location"));
                c.setCampDate(rs.getDate("camp_date"));
                int bankId = rs.getInt("bank_id");
                c.setBankId(rs.wasNull() ? null : bankId);
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching camps: " + e.getMessage());
        }
        return list;
    }

    // ---------------------------------------------------------- UPDATE
    public boolean updateCamp(DonationCamp c) {
        String sql = "UPDATE donation_camp SET camp_name=?, location=?, camp_date=?, bank_id=? WHERE camp_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getCampName());
            ps.setString(2, c.getLocation());
            ps.setDate(3, c.getCampDate());
            if (c.getBankId() != null) ps.setInt(4, c.getBankId());
            else ps.setNull(4, Types.INTEGER);
            ps.setInt(5, c.getCampId());

            int rows = ps.executeUpdate();
            if (rows == 0) System.out.println("No camp found with ID " + c.getCampId());
            else System.out.println("Donation camp updated successfully.");
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error updating camp: " + e.getMessage());
            return false;
        }
    }

    // ---------------------------------------------------------- DELETE
    public boolean deleteCamp(int campId) {
        String sql = "DELETE FROM donation_camp WHERE camp_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, campId);
            int rows = ps.executeUpdate();
            if (rows == 0) System.out.println("No camp found with ID " + campId);
            else System.out.println("Donation camp deleted successfully.");
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting camp: " + e.getMessage());
            return false;
        }
    }
}
