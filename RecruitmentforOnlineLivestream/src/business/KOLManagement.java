package business;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import model.I_KOLService;
import model.KOL;

public class KOLManagement implements I_KOLService {
    private List<KOL> kolList;
    private boolean hasUnsavedChanges;
    
    public KOLManagement () {
        this.kolList = new ArrayList<>();
        this.hasUnsavedChanges = false;
    }
    
    @Override
    public boolean addKOL(KOL kol) {
        if (findById(kol.getKolId()) != null) {
            return false;
        }
        kolList.add(kol);
        hasUnsavedChanges = true;
        return true;
    }

    @Override
    public boolean updateKOL(String kolId, KOL updatedKol) {
        KOL existingKol = findById(kolId);
        if (existingKol == null) {
            return false;
        }
        
        int index = kolList.indexOf(existingKol);
        updatedKol.setKolId(kolId);
        kolList.set(index, updatedKol);
        hasUnsavedChanges = true;
        return true;
    }

    @Override
    public boolean deleteKOL(String kolId) {
        KOL kol = findById(kolId);
        if (kol != null) {
            kolList.remove(kol);
            hasUnsavedChanges = true;
            return true;
        }
        return false;
    }

    @Override
    public List<KOL> getAllKOLs() {
        return new ArrayList<>(kolList);
    }

    @Override
    public List<KOL> searchByName(String name) {
        return kolList.stream()
                .filter(kol -> kol.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<KOL> filterByCategory(String categoryCode) {
        return kolList.stream()
                .filter(kol -> kol.getCategoryCode().equalsIgnoreCase(categoryCode))
                .collect(Collectors.toList());
    }

    @Override
    public KOL findById(String kolId) {
         return kolList.stream()
                .filter(kol -> kol.getKolId().equals(kolId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(kolList);
            hasUnsavedChanges = false;
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            kolList = (List<KOL>) ois.readObject();
            hasUnsavedChanges = false;
            return true;
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }
    
    public boolean hasUnsavedChanges() {
        return hasUnsavedChanges;
    }

    public Map<String, Double> getPlatformStatistics() {
        Map<String, List<KOL>> platformGroups = kolList.stream()
                .collect(Collectors.groupingBy(KOL::getPlatformCode));
        
        Map<String, Double> statistics = new HashMap<>();
        for (Map.Entry<String, List<KOL>> entry : platformGroups.entrySet()) {
            String platform = entry.getKey();
            List<KOL> kols = entry.getValue();
            double avgCommission = kols.stream()
                    .mapToInt(KOL::getCommissionRate)
                    .average()
                    .orElse(0.0);
            statistics.put(platform, avgCommission);
        }
        return statistics;
    }

    public Map<String, Integer> getPlatformCounts() {
        return kolList.stream()
                .collect(Collectors.groupingBy(
                    KOL::getPlatformCode,
                    Collectors.collectingAndThen(Collectors.counting(), Math::toIntExact)
                ));
    }
}
