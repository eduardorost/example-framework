package com.example.tenant.storage;

public class TenantStorage {
    private static final InheritableThreadLocal<String> TENANT = new InheritableThreadLocal<>();

    private TenantStorage() {
    }

    public static void setTenantId(String tenantId) {
        TENANT.set(tenantId);
    }

    public static String getTenantId() {
        return TENANT.get();
    }
}
