package tools;


public interface I_Validator {
    boolean isValidKolId(String kolId);
    boolean isValidName(String name);
    boolean isValidPhoneNumber(String phoneNumber);
    boolean isValidEmail(String email);
    boolean isValidPlatformCode(String platformCode);
    boolean isValidFollowerCount(String followerCount);
}
