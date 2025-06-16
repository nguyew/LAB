package dto;

import java.util.List;

/* Interface for a group of objects
 */
/**
 *
 * @author Hoa Doan
 */
public interface I_List {

    boolean loadFromFile(String filePath);

    boolean enterGuest();
    
    boolean updateGuest(String nationalID);

    List<Guest> searchGuest(String guestID);

    Guest deleteGuest(String nationalID);
    void displayAvaiableRoom();

    void listVacantRooms();

    double monthlyRevenueReport();

    double revenueReportByRoomType();

    boolean saveGuestInformation();
}
