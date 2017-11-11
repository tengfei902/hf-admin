package hf.admin.enums;

public enum UserType {
    INIT(0,"初始"),
    ADMIN(1,"管理员"),
    AGENT(2,"代理商"),
    CUSTOMER(3,"普通商户"),
    SUPER_ADMIN(4,"超级管理员");

    private int value;
    private String desc;

    UserType(int value,String desc) {
            this.value = value;
            this.desc = desc;
        }

    public static UserType parse(int value) {
        for(UserType type:UserType.values()) {
            if(type.value == value) {
                return type;
            }
        }
        return null;
    }

    public String getDesc() {
        return this.desc;
        }

    public int getValue() {
        return this.value;
    }
}