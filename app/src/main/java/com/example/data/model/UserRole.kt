package com.example.data.model

enum class UserRole(val titleAr: String, val titleEn: String, val isAdminPortal: Boolean) {
    OWNER("صاحب المؤسسة", "Business Owner", true),
    EXECUTIVE_DIRECTOR("المدير التنفيذي", "Executive Director", true),
    OPERATIONS_MANAGER("مدير التشغيل", "Operations Manager", true),
    KITCHEN_MANAGER("مدير المطبخ", "Kitchen Manager", true),
    SITE_OFFICER("مسؤول الموقع", "Site Officer", true),
    HR_OFFICER("مسؤول الموارد البشرية", "HR Officer", true),
    ACCOUNTANT("المحاسب", "Accountant", true),
    PROCUREMENT_OFFICER("مسؤول المشتريات", "Procurement Officer", true),
    INVENTORY_OFFICER("مسؤول المخزون", "Inventory Officer", true),
    QUALITY_OFFICER("مسؤول الجودة", "Quality Officer", true),
    HYGIENE_OFFICER("مسؤول الهايجين", "Hygiene Officer", true),
    MARKETING_OFFICER("مسؤول التسويق والعروض", "Marketing Officer", true),
    SITE_SUPERVISOR("مشرف الموقع", "Site Supervisor", false),
    EMPLOYEE("موظف الموقع", "Site Employee", false),
    CLIENT_USER("عميل / مستخدم البوابة", "Client Portal User", false)
}
