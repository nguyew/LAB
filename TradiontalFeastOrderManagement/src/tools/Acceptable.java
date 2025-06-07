package tools;

public interface Acceptable {
    String CUS_ID_VALID = "^[CcGgKk]\\d{4}$";
    String NAME_VALID = "^.{2,25}$";
    String PHONE_VALID = "^0\\d{9}$";
    String EMAIL_VALID = "^[\\w.-]+@[\\w.-]+\\.\\w{2,}$";
    String POSITIVE_INTEGER = "^[1-9]\\d*$";
    String MENU_ID_VALID = "^\\w{5}$";  

    static boolean isValid(String data, String pattern) {
        return data.matches(pattern);
    }
}
