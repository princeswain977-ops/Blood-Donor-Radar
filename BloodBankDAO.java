import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the BLOOD_BANK table.
 * Demonstrates all four CRUD operations using JDBC PreparedStatement.
 */
public class BloodBankDAO {

    // ---------------------------------------------------------- CREATE
    public boolean addBloodBank(BloodBank b) {
        String sql = "INSERT INTO blood_bank (bank_name, address, city, contact_number) VALUES (?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, b.getBankName());
            ps.setString(2, b.getAddress());
            ps.setString(3, b.getCity());
            ps.setString(4, b.getContactNumber());

            int rows = ps.executeUpdate();
            System.out.println("Blood bank added successfully. Rows affected: " + rows);
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error adding blood bank: " + e.getMessage());
            return false;
        }
    }

    // ------------------------------------------------------------ READ
    public List<BloodBank> getAllBloodBanks() {
        List<BloodBank> list = new ArrayList<>();
        String sql = "SELECT * FROM blood_bank";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                BloodBank b = new BloodBank();
                b.setBankId(rs.getInt("bank_id"));
                b.setBankName(rs.getString("bank_name"));
                b.setAddress(rs.getString("address"));
                b.setCity(rs.getString("city"));
                b.setContactNumber(rs.getString("contact_number"));
                list.add(b);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching blood banks: " + e.getMessage());
        }
        return list;
    }

    // ---------------------------------------------------------- UPDATE
    public boolean updateBloodBank(BloodBank b) {
        String sql = "UPDATE blood_bank SET bank_name=?, address=?, city=?, contact_number=? WHERE bank_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, b.getBankName());
            ps.setString(2, b.getAddress());
            ps.setString(3, b.getCity());
            ps.setString(4, b.getContactNumber());
            ps.setInt(5, b.getBankId());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("No blood bank found with ID " + b.getBankId());
            } else {
                System.out.println("Blood bank updated successfully.");
            }
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error updating blood bank: " + e.getMessage());
            return false;
        }
    }

    // ---------------------------------------------------------- DELETE
    public boolean deleteBloodBank(int bankId) {
        String sql = "DELETE FROM blood_bank WHERE bank_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bankId);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("No blood bank found with ID " + bankId);
            } else {
                System.out.println("Blood bank deleted successfully.");
            }
            return rows > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            // Fires if donors/camps still reference this bank in a way the
            // FK's ON DELETE rule does not resolve automatically.
            System.out.println("Cannot delete: this blood bank is still referenced by other records.");
            return false;
        } catch (SQLException e) {
            System.out.println("Error deleting blood bank: " + e.getMessage());
            return false;
        }
    }
}
