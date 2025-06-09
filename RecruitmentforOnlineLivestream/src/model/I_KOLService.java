package model;

import java.util.List;

public interface I_KOLService {
    boolean addKOL(KOL kol);
    boolean updateKOL(String kolId, KOL updatedKol);
    boolean deleteKOL(String kolId);
    List<KOL> getAllKOLs();
    List<KOL> searchByName(String name);
    List<KOL> filterByCategory(String categoryCode);
    KOL findById(String kolId);
    boolean saveToFile(String filename);
    boolean loadFromFile(String filename);
}
