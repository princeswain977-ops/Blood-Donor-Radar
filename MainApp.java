import java.sql.Date;
import java.util.List;
import java.util.Scanner;


public class MainApp {

    static Scanner sc = new Scanner(System.in);
    static DonorDAO donorDAO = new DonorDAO();
    static BloodBankDAO bankDAO = new BloodBankDAO();
    static DonationCampDAO campDAO = new DonationCampDAO();
    static DonationDAO donationDAO = new DonationDAO();

    public static void main(String[] args) {
        int choice = -1;
        do {
            printMainMenu();
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
                switch (choice) {
                    case 1: donorMenu(); break;
                    case 2: bloodBankMenu(); break;
                    case 3: campMenu(); break;
                    case 4: donationMenu(); break;
                    case 5: searchDonors(); break;
                    case 0: System.out.println("Exiting Blood Donor Radar. Goodbye!"); break;
                    default: System.out.println("Invalid choice. Please select a valid option.");
                }
            } catch (NumberFormatException e) {
                // Demonstrates handling of a NON-database exception too.
                System.out.println("Invalid input! Please enter a number.");
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        } while (choice != 0);
        sc.close();
    }

    // ============================================================ MAIN MENU
    static void printMainMenu() {
        System.out.println("\n===================== BLOOD DONOR RADAR =====================");
        System.out.println("1. Manage Donors");
        System.out.println("2. Manage Blood Banks");
        System.out.println("3. Manage Donation Camps");
        System.out.println("4. Manage Donation Records");
        System.out.println("5. Radar Search (find available donors)");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }

    // ============================================================ DONOR MENU
    static void donorMenu() {
        System.out.println("\n-- Donor Management --");
        System.out.println("1. Add Donor (Create)");
        System.out.println("2. View All Donors (Read)");
        System.out.println("3. Update Donor (Update)");
        System.out.println("4. Delete Donor (Delete)");
        System.out.print("Enter choice: ");
        try {
            int c = Integer.parseInt(sc.nextLine().trim());
            switch (c) {
                case 1: addDonor(); break;
                case 2: viewDonors(); break;
                case 3: updateDonor(); break;
                case 4: deleteDonor(); break;
                default: System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
        }
    }

    static void addDonor() {
        System.out.print("Full name: ");        String name = sc.nextLine();
        System.out.print("Blood group (e.g. O+): "); String bg = sc.nextLine();
        System.out.print("Contact number: ");    String contact = sc.nextLine();
        System.out.print("Email: ");             String email = sc.nextLine();
        System.out.print("City: ");              String city = sc.nextLine();
        System.out.print("Blood Bank ID (blank if none): "); String bankStr = sc.nextLine();

        try {
            Integer bankId = bankStr.isEmpty() ? null : Integer.parseInt(bankStr);
            Donor d = new Donor(name, bg, contact, email, city, null, "Available", bankId);
            donorDAO.addDonor(d);
        } catch (NumberFormatException e) {
            System.out.println("Bank ID must be a whole number.");
        }
    }

    static void viewDonors() {
        List<Donor> donors = donorDAO.getAllDonors();
        if (donors.isEmpty()) {
            System.out.println("No donors found.");
        } else {
            donors.forEach(System.out::println);
        }
    }

    static void updateDonor() {
        try {
            System.out.print("Donor ID to update: ");
            int id = Integer.parseInt(sc.nextLine().trim());

            System.out.print("New full name: ");    String name = sc.nextLine();
            System.out.print("New blood group: ");  String bg = sc.nextLine();
            System.out.print("New contact number: "); String contact = sc.nextLine();
            System.out.print("New email: ");        String email = sc.nextLine();
            System.out.print("New city: ");         String city = sc.nextLine();
            System.out.print("New availability (Available/Not Available): "); String status = sc.nextLine();

            Donor d = new Donor(name, bg, contact, email, city, null, status, null);
            d.setDonorId(id);
            donorDAO.updateDonor(d);
        } catch (NumberFormatException e) {
            System.out.println("Donor ID must be a whole number.");
        }
    }

    static void deleteDonor() {
        try {
            System.out.print("Donor ID to delete: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            donorDAO.deleteDonor(id);
        } catch (NumberFormatException e) {
            System.out.println("Donor ID must be a whole number.");
        }
    }

    static void searchDonors() {
        System.out.print("Blood group to search (blank = any): "); String bg = sc.nextLine();
        System.out.print("City to search (blank = any): ");        String city = sc.nextLine();
        List<Donor> results = donorDAO.searchDonors(bg, city);
        if (results.isEmpty()) System.out.println("No matching available donors found.");
        else results.forEach(System.out::println);
    }

    // ============================================================ BLOOD BANK MENU
    static void bloodBankMenu() {
        System.out.println("\n-- Blood Bank Management --");
        System.out.println("1. Add Blood Bank (Create)");
        System.out.println("2. View All Blood Banks (Read)");
        System.out.println("3. Update Blood Bank (Update)");
        System.out.println("4. Delete Blood Bank (Delete)");
        System.out.print("Enter choice: ");
        try {
            int c = Integer.parseInt(sc.nextLine().trim());
            switch (c) {
                case 1: {
                    System.out.print("Bank name: ");   String name = sc.nextLine();
                    System.out.print("Address: ");     String addr = sc.nextLine();
                    System.out.print("City: ");        String city = sc.nextLine();
                    System.out.print("Contact number: "); String contact = sc.nextLine();
                    bankDAO.addBloodBank(new BloodBank(name, addr, city, contact));
                    break;
                }
                case 2: {
                    List<BloodBank> banks = bankDAO.getAllBloodBanks();
                    if (banks.isEmpty()) System.out.println("No blood banks found.");
                    else banks.forEach(System.out::println);
                    break;
                }
                case 3: {
                    System.out.print("Bank ID to update: "); int id = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("New bank name: ");   String name = sc.nextLine();
                    System.out.print("New address: ");     String addr = sc.nextLine();
                    System.out.print("New city: ");        String city = sc.nextLine();
                    System.out.print("New contact number: "); String contact = sc.nextLine();
                    BloodBank b = new BloodBank(name, addr, city, contact);
                    b.setBankId(id);
                    bankDAO.updateBloodBank(b);
                    break;
                }
                case 4: {
                    System.out.print("Bank ID to delete: "); int id = Integer.parseInt(sc.nextLine().trim());
                    bankDAO.deleteBloodBank(id);
                    break;
                }
                default: System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
        }
    }

    // ============================================================ CAMP MENU
    static void campMenu() {
        System.out.println("\n-- Donation Camp Management --");
        System.out.println("1. Add Camp (Create)");
        System.out.println("2. View All Camps (Read)");
        System.out.println("3. Update Camp (Update)");
        System.out.println("4. Delete Camp (Delete)");
        System.out.print("Enter choice: ");
        try {
            int c = Integer.parseInt(sc.nextLine().trim());
            switch (c) {
                case 1: {
                    System.out.print("Camp name: ");  String name = sc.nextLine();
                    System.out.print("Location: ");   String loc = sc.nextLine();
                    System.out.print("Camp date (YYYY-MM-DD): "); Date date = Date.valueOf(sc.nextLine().trim());
                    System.out.print("Blood Bank ID (blank if none): "); String bankStr = sc.nextLine();
                    Integer bankId = bankStr.isEmpty() ? null : Integer.parseInt(bankStr);
                    campDAO.addCamp(new DonationCamp(name, loc, date, bankId));
                    break;
                }
                case 2: {
                    List<DonationCamp> camps = campDAO.getAllCamps();
                    if (camps.isEmpty()) System.out.println("No camps found.");
                    else camps.forEach(System.out::println);
                    break;
                }
                case 3: {
                    System.out.print("Camp ID to update: "); int id = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("New camp name: "); String name = sc.nextLine();
                    System.out.print("New location: ");  String loc = sc.nextLine();
                    System.out.print("New camp date (YYYY-MM-DD): "); Date date = Date.valueOf(sc.nextLine().trim());
                    System.out.print("New Blood Bank ID (blank if none): "); String bankStr = sc.nextLine();
                    Integer bankId = bankStr.isEmpty() ? null : Integer.parseInt(bankStr);
                    DonationCamp camp = new DonationCamp(name, loc, date, bankId);
                    camp.setCampId(id);
                    campDAO.updateCamp(camp);
                    break;
                }
                case 4: {
                    System.out.print("Camp ID to delete: "); int id = Integer.parseInt(sc.nextLine().trim());
                    campDAO.deleteCamp(id);
                    break;
                }
                default: System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
        } catch (IllegalArgumentException e) {
            // Date.valueOf throws this for a badly-formatted date string.
            System.out.println("Date must be in YYYY-MM-DD format.");
        }
    }

    // ============================================================ DONATION MENU
    static void donationMenu() {
        System.out.println("\n-- Donation Record Management --");
        System.out.println("1. Add Donation Record (Create)");
        System.out.println("2. View All Donation Records (Read)");
        System.out.println("3. Update Donation Record (Update)");
        System.out.println("4. Delete Donation Record (Delete)");
        System.out.print("Enter choice: ");
        try {
            int c = Integer.parseInt(sc.nextLine().trim());
            switch (c) {
                case 1: {
                    System.out.print("Donor ID: ");  int donorId = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Camp ID (blank if none): "); String campStr = sc.nextLine();
                    Integer campId = campStr.isEmpty() ? null : Integer.parseInt(campStr);
                    System.out.print("Donation date (YYYY-MM-DD): "); Date date = Date.valueOf(sc.nextLine().trim());
                    System.out.print("Units donated: "); int units = Integer.parseInt(sc.nextLine().trim());
                    donationDAO.addDonation(new Donation(donorId, campId, date, units));
                    break;
                }
                case 2: {
                    List<Donation> donations = donationDAO.getAllDonations();
                    if (donations.isEmpty()) System.out.println("No donation records found.");
                    else donations.forEach(System.out::println);
                    break;
                }
                case 3: {
                    System.out.print("Donation ID to update: "); int id = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("New Donor ID: "); int donorId = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("New Camp ID (blank if none): "); String campStr = sc.nextLine();
                    Integer campId = campStr.isEmpty() ? null : Integer.parseInt(campStr);
                    System.out.print("New donation date (YYYY-MM-DD): "); Date date = Date.valueOf(sc.nextLine().trim());
                    System.out.print("New units donated: "); int units = Integer.parseInt(sc.nextLine().trim());
                    Donation don = new Donation(donorId, campId, date, units);
                    don.setDonationId(id);
                    donationDAO.updateDonation(don);
                    break;
                }
                case 4: {
                    System.out.print("Donation ID to delete: "); int id = Integer.parseInt(sc.nextLine().trim());
                    donationDAO.deleteDonation(id);
                    break;
                }
                default: System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Date must be in YYYY-MM-DD format.");
        }
    }
}
