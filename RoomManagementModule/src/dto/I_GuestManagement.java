package dto;

import java.util.List;

public interface I_GuestManagement {
    boolean addGuest(Guest guest);
    boolean updateGuest(String nationalID, Guest updatedGuest);
    Guest searchGuestByNationalId(String nationalID);
    boolean deleteGuestReservation(String nationalID);
    List<Guest> getAllGuests();
    boolean saveGuestInfo(String filePath);
    double calculateMonthlyRevenue(int month, int year);
    double calculateRevenueByRoomType(String roomType);
}
