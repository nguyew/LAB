package dto;

import java.util.List;


public interface I_RoomManagement {
    boolean loadRoomsFromFile(String filePath);
    void displayAvailableRooms();
    List<Room> getAvailableRooms();
    Room getRoomById(String roomID);
    boolean isRoomAvailable(String roomID);
    void setRoomOccupied(String roomID, boolean occupied);
}
