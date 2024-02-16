package tech.olatunbosun.wastemanagement.usermanagement.utility.enums;


public enum UserStatus {

        ACTIVE("active"),
        INACTIVE("inactive"),
        PENDING("pending"),
        SUSPENDED("suspended");

        private final String userStatus;

        UserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public String getUserStatus() {
            return userStatus;
        }
}
