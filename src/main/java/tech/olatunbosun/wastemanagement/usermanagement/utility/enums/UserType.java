package tech.olatunbosun.wastemanagement.usermanagement.utility.enums;

public enum UserType {

    ADMIN("admin"),
    PARTNER("partner"),
    WASTE_WARRIOR("waste_warrior");

    private final String userType;

    UserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }
}
