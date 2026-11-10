package com.example.data.local

import com.example.data.model.*

object DatabaseInitializer {

    suspend fun populateInitialData(dao: CateringDao) {
        // --- 1. Initial Users ---
        val users = listOf(
            UserAccount(
                id = "USER_OWNER",
                username = "owner",
                fullNameAr = "م. أحمد عبد الرحمن (رئيس مجلس الإدارة)",
                fullNameEn = "Eng. Ahmed Abdelrahman (Chairman & Owner)",
                role = UserRole.OWNER,
                siteId = "ALL",
                kitchenId = "ALL",
                departmentAr = "الإدارة العليا - الشركة الوطنية اللوجستية",
                departmentEn = "Executive Board - National Logistics",
                employeeNumber = "NL-001",
                phone = "01000000001",
                cashbackBalance = 0.0
            ),
            UserAccount(
                id = "USER_OPS",
                username = "ops_mgr",
                fullNameAr = "ك. محمود خليل (مدير التشغيل العام)",
                fullNameEn = "Chef Mahmoud Khalil (General Ops Manager)",
                role = UserRole.OPERATIONS_MANAGER,
                siteId = "ALL",
                kitchenId = "ALL",
                departmentAr = "إدارة العمليات المركزية",
                departmentEn = "Central Operations Directorate",
                employeeNumber = "NL-004",
                phone = "01000000004",
                cashbackBalance = 0.0
            ),
            UserAccount(
                id = "USER_QUALITY",
                username = "quality",
                fullNameAr = "د. سارة عزمي (مسؤول سلامة الغذاء والجودة)",
                fullNameEn = "Dr. Sara Azmy (Quality & Hygiene Officer)",
                role = UserRole.QUALITY_OFFICER,
                siteId = "ALL",
                kitchenId = "ALL",
                departmentAr = "إدارة توكيد الجودة والهايجين",
                departmentEn = "QA & Food Safety Directorate",
                employeeNumber = "NL-008",
                phone = "01000000008",
                cashbackBalance = 0.0
            ),
            UserAccount(
                id = "USER_FINANCE",
                username = "finance",
                fullNameAr = "أ. إبراهيم فؤاد (رئيس الحسابات والتحصيل)",
                fullNameEn = "Mr. Ibrahim Fouad (Chief Accountant)",
                role = UserRole.ACCOUNTANT,
                siteId = "ALL",
                kitchenId = "ALL",
                departmentAr = "الإدارة المالية والمراجعة",
                departmentEn = "Finance & Audit Directorate",
                employeeNumber = "NL-006",
                phone = "01000000006",
                cashbackBalance = 0.0
            ),
            UserAccount(
                id = "USER_KITCHEN_HANGER",
                username = "chef_hanger",
                fullNameAr = "شيف طارق حسني (مدير مطبخ هنجر استيشن)",
                fullNameEn = "Chef Tarek Hosny (Hanger Station Kitchen Manager)",
                role = UserRole.KITCHEN_MANAGER,
                siteId = "ALL",
                kitchenId = "KITCHEN_HANGER",
                departmentAr = "مطبخ هنجر استيشن المركزي",
                departmentEn = "Hanger Station Central Kitchen",
                employeeNumber = "NL-101",
                phone = "01000000101",
                cashbackBalance = 0.0
            ),
            // Client & Site Employees (Strictly Isolated to their sites)
            UserAccount(
                id = "EMP_GERMAN_CEMENT",
                username = "worker_cement",
                fullNameAr = "المهندس هاني زكي",
                fullNameEn = "Eng. Hany Zaki",
                role = UserRole.EMPLOYEE,
                siteId = "GERMAN_CEMENT",
                departmentAr = "قسم الصيانة الميكانيكية",
                departmentEn = "Mechanical Maintenance Dept",
                employeeNumber = "GC-4820",
                phone = "01123456780",
                cashbackBalance = 145.0
            ),
            UserAccount(
                id = "EMP_ROYAL_HOSPITAL",
                username = "nurse_royal",
                fullNameAr = "الأستاذة مروة سليم",
                fullNameEn = "Ms. Marwa Selim",
                role = UserRole.EMPLOYEE,
                siteId = "ROYAL_HOSPITAL",
                departmentAr = "تمريض الرعاية المركزة",
                departmentEn = "ICU Nursing Dept",
                employeeNumber = "RH-1104",
                phone = "01234567890",
                cashbackBalance = 80.0
            ),
            UserAccount(
                id = "EMP_SHOROUK_HOSPITAL",
                username = "admin_shorouk",
                fullNameAr = "أحمد رضوان",
                fullNameEn = "Ahmed Radwan",
                role = UserRole.EMPLOYEE,
                siteId = "SHOROUK_HOSPITAL",
                departmentAr = "الاستقبال والخدمات الطبية",
                departmentEn = "Medical Services Desk",
                employeeNumber = "SH-229",
                phone = "01099887766",
                cashbackBalance = 50.0
            )
        )
        dao.insertUsers(users)

        // --- 2. Central Kitchens (Section 4) ---
        val kitchens = listOf(
            CentralKitchen(
                id = "KITCHEN_HANGER",
                nameAr = "مطبخ هنجر استيشن المركزي",
                nameEn = "Hanger Station Central Kitchen",
                locationAr = "المنطقة اللوجستية المركزية - القاهرة",
                locationEn = "Central Logistics Zone - Cairo",
                managerName = "شيف طارق حسني",
                staffCount = 42,
                dailyCapacity = 6000,
                todayProductionCount = 3850,
                rawMaterialHealthScore = 98,
                hygieneScore = 99,
                qualityScore = 97,
                activeOrdersCount = 14
            ),
            CentralKitchen(
                id = "KITCHEN_BADR",
                nameAr = "مطبخ مول العين — مدينة بدر",
                nameEn = "Mall Al Ain Central Kitchen — Badr City",
                locationAr = "مدينة بدر - بجوار مول العين",
                locationEn = "Badr City - Near Mall Al Ain",
                managerName = "شيف سامح عبد العظيم",
                staffCount = 35,
                dailyCapacity = 4500,
                todayProductionCount = 2920,
                rawMaterialHealthScore = 96,
                hygieneScore = 98,
                qualityScore = 95,
                activeOrdersCount = 10
            ),
            CentralKitchen(
                id = "KITCHEN_RAYAN",
                nameAr = "مطبخ الريان — اللاهون، غرب بني سويف",
                nameEn = "Al Rayan Central Kitchen — Lahoon, West Beni Suef",
                locationAr = "اللاهون - الطريق الغربي، بني سويف",
                locationEn = "Lahoon - Western Road, Beni Suef",
                managerName = "شيف حسام النجار",
                staffCount = 38,
                dailyCapacity = 5500,
                todayProductionCount = 3400,
                rawMaterialHealthScore = 97,
                hygieneScore = 97,
                qualityScore = 96,
                activeOrdersCount = 12
            )
        )
        dao.insertCentralKitchens(kitchens)

        // --- 3. Active Sites & Clients (Section 5) ---
        val sites = listOf(
            CateringSite(
                id = "ROYAL_HOSPITAL",
                nameAr = "مستشفى رويال — ROYAL HOSPITAL",
                nameEn = "Royal Hospital",
                categoryAr = "مستشفيات ورعاية صحية متقدمة",
                categoryEn = "Advanced Healthcare & Hospital",
                contactPerson = "د. كريم سامي (مدير الشؤون الفندقية)",
                phone = "01001239871",
                dailyMealsContracted = 850,
                hasCanteen = false,
                assignedKitchenId = "KITCHEN_HANGER",
                assignedKitchenNameAr = "مطبخ هنجر استيشن المركزي",
                assignedKitchenNameEn = "Hanger Station Central Kitchen",
                contractValidUntil = "2027-12-31"
            ),
            CateringSite(
                id = "GERMAN_CEMENT",
                nameAr = "الشركة الألمانية للأسمنت — GERMAN CEMENT",
                nameEn = "German Cement Company",
                categoryAr = "صناعات ثقيلة ومصانع إعاشة",
                categoryEn = "Heavy Industries & Manufacturing",
                contactPerson = "م. شريف عادل (مدير الخدمات الإدارية)",
                phone = "01112233445",
                dailyMealsContracted = 1600,
                hasCanteen = true,
                assignedKitchenId = "KITCHEN_RAYAN",
                assignedKitchenNameAr = "مطبخ الريان — اللاهون، غرب بني سويف",
                assignedKitchenNameEn = "Al Rayan Central Kitchen",
                contractValidUntil = "2028-06-30"
            ),
            CateringSite(
                id = "SHOROUK_HOSPITAL",
                nameAr = "مستشفى الشروق — EL SHOROUK HOSPITAL",
                nameEn = "El Shorouk Hospital",
                categoryAr = "رعاية طبية تخصصية وإعاشة متكاملة",
                categoryEn = "Specialized Medical Care",
                contactPerson = "أ. هدى منصور (مسؤولة التعاقدات والخدمات)",
                phone = "01223344556",
                dailyMealsContracted = 720,
                hasCanteen = false,
                assignedKitchenId = "KITCHEN_BADR",
                assignedKitchenNameAr = "مطبخ مول العين — مدينة بدر",
                assignedKitchenNameEn = "Mall Al Ain Central Kitchen",
                contractValidUntil = "2027-09-30"
            )
        )
        dao.insertCateringSites(sites)

        // --- 4. Pending Contracts / Pipeline (Section 6) ---
        val pending = listOf(
            PendingContract(
                id = "PEND_NAGWA",
                nameAr = "نجوى تكنولوجي — NAGWA TECHNOLOGY",
                nameEn = "Nagwa Technology",
                activityAr = "تطوير برمجيات ومحتوى تعليمي رقمي",
                activityEn = "EdTech & Software Development",
                contactPerson = "أ. رامي حلمي (Head of Facilities)",
                phone = "01099881122",
                contactDate = "2026-10-15",
                proposalStatus = "NEGOTIATING",
                proposalStatusAr = "تفاوض على شروط العقد وبنود السداد",
                proposalStatusEn = "Contract Negotiations",
                estimatedMealsPerDay = 650,
                nextActionAr = "إرسال ملحق الأسعار النهائية والوجبات الصحية",
                nextActionEn = "Send revised pricing schedule",
                lastUpdate = "منذ يومين",
                notes = "طلبوا التركيز على وجبات السناك الصحي ووجبة الغداء الساخنة للمهندسين."
            ),
            PendingContract(
                id = "PEND_SHOROUK_PRESS",
                nameAr = "مطبعة الشروق — AL SHOROUK PRINTING PRESS",
                nameEn = "Al Shorouk Printing Press",
                activityAr = "طباعة ونشر صناعي متعدد الورديات",
                activityEn = "Industrial Printing & Publishing",
                contactPerson = "م. خالد دسوقي (مدير العمليات)",
                phone = "01155443322",
                contactDate = "2026-10-22",
                proposalStatus = "QUOTE_SENT",
                proposalStatusAr = "عرض مرسل - انتظار مراجعة مجلس الإدارة",
                proposalStatusEn = "Proposal Sent - In Review",
                estimatedMealsPerDay = 900,
                nextActionAr = "متابعة هاتفية مع الإدارة المالية",
                nextActionEn = "Follow-up phone call with finance",
                lastUpdate = "أمس",
                notes = "تغذية ورديات ليلية ونهارية تشمل وجبات ساخنة وجافة."
            ),
            PendingContract(
                id = "PEND_MR_MINA",
                nameAr = "مستر مينا — متعهد عمالة ومشاريع",
                nameEn = "Mr. Mina - Projects Contractor",
                activityAr = "مقاولات عامة وإسكان عمالي بالمواقع",
                activityEn = "General Contracting & Workforce Camps",
                contactPerson = "السيد / مينا جرجس",
                phone = "01277665544",
                contactDate = "2026-10-28",
                proposalStatus = "PREPARING_QUOTE",
                proposalStatusAr = "عرض سعر قيد الإعداد",
                proposalStatusEn = "Preparing Quotation",
                estimatedMealsPerDay = 1200,
                nextActionAr = "احتساب تكلفة التوزيع اللوجستي لموقع العاصمة الإدارية",
                nextActionEn = "Calculate transport logistics to New Capital",
                lastUpdate = "منذ 3 أيام",
                notes = "يحتاج وجبات إفطار وغداء للعمالة المتمركزة بالمشروع."
            ),
            PendingContract(
                id = "PEND_UNIMAC",
                nameAr = "يونيماك — مصنع أدوية ومستحضرات طبية",
                nameEn = "Unimac Pharmaceutical Industries",
                activityAr = "صناعات دوائية ومختبرات بيولوجية",
                activityEn = "Pharmaceutical & Bio Labs",
                contactPerson = "د. وليد فخري (مدير الموارد البشرية)",
                phone = "01033221100",
                contactDate = "2026-11-02",
                proposalStatus = "CONTACTED",
                proposalStatusAr = "تم التواصل وزيارة وفد الجودة للموقع",
                proposalStatusEn = "Site Visit Completed",
                estimatedMealsPerDay = 500,
                nextActionAr = "تقديم نموذج قائمة وجبات الهايجين الطبي والكنتين",
                nextActionEn = "Submit medical hygiene meal plans",
                lastUpdate = "اليوم",
                notes = "يشترطون معايير ISO 22000 وفحوصات دورية لسلامة الغذاء."
            ),
            PendingContract(
                id = "PEND_KASR_ALAINY",
                nameAr = "القصر العيني الفرنساوي — KASR AL AINY FRENCH",
                nameEn = "Kasr Al Ainy French Hospital",
                activityAr = "مستشفى جامعي علاجي وجراحي",
                activityEn = "University Tertiary Hospital",
                contactPerson = "أ.د. حازم ممدوح (لجنة التغذية العلاجية)",
                phone = "01211223344",
                contactDate = "2026-11-06",
                proposalStatus = "AWAITING_REPLY",
                proposalStatusAr = "انتظار رد اللجنة الطبية على كراسة الشروط",
                proposalStatusEn = "Awaiting Medical Committee Reply",
                estimatedMealsPerDay = 2100,
                nextActionAr = "حضور جلسة الاستفسارات الفنية",
                nextActionEn = "Attend technical Q&A session",
                lastUpdate = "منذ 4 ساعات",
                notes = "مناقصة سنوية لإطعام المرضى والكادر الطبي والتمريضي."
            )
        )
        dao.insertPendingContracts(pending)

        // --- 5. Menus & Meals (Section 8) ---
        // Codes: LZ-01..LZ-05, SL-04, RH-01, GC-01..
        val meals = listOf(
            MealItem(
                id = "MEAL_LZ01",
                code = "LZ-01",
                nameAr = "وجبة الدجاج المشوي بالأعشاب مع أرز بسمتي وخضار سوتيه",
                nameEn = "Herb Roasted Chicken with Basmati Rice & Sauteed Veggies",
                category = MealCategory.HOT_MEAL,
                ingredientsAr = "نصف دجاجة طازجة، أرز بسمتي ذهبي، كوسة وجزر وبروكلي، بهارات شيف خاصة",
                ingredientsEn = "Fresh half chicken, golden basmati, zucchini, carrots, broccoli, chef spices",
                portionSize = "480 جم",
                calories = 680,
                approvedRetailPrice = 115.0,
                internalCostPrice = 72.0,
                siteId = "ALL",
                kitchenId = "KITCHEN_HANGER"
            ),
            MealItem(
                id = "MEAL_LZ02",
                code = "LZ-02",
                nameAr = "وجبة كفتة اللحم البلدي المشوية على الفحم مع مكرونة بالصلصة",
                nameEn = "Charcoal Grilled Kofta with Tomato Pasta & Tahina",
                category = MealCategory.LUNCH,
                ingredientsAr = "لحم بقري بلدي مفروم 200جم، بصل وتوابل شرقية، مكرونة بنا بالصلصة، سلطة طحينة، خبز طازج",
                ingredientsEn = "Minced local beef, penne tomato pasta, oriental tahini, fresh bread",
                portionSize = "520 جم",
                calories = 750,
                approvedRetailPrice = 130.0,
                internalCostPrice = 84.0,
                siteId = "GERMAN_CEMENT",
                kitchenId = "KITCHEN_RAYAN"
            ),
            MealItem(
                id = "MEAL_LZ03",
                code = "LZ-03",
                nameAr = "وجبة إفطار عمالي متكامل — فطور الطاقة الصباحي",
                nameEn = "Industrial Morning Breakfast Meal",
                category = MealCategory.BREAKFAST,
                ingredientsAr = "فول مدمس بالسمن البلدي، بيض مسلوق، جبنة بيضاء بالطماطم والزيتون، رغيفين بلدي، ثمرة فاكهة",
                ingredientsEn = "Fava beans, boiled egg, white cheese with tomato & olives, 2 loaves, fruit",
                portionSize = "400 جم",
                calories = 540,
                approvedRetailPrice = 45.0,
                internalCostPrice = 26.0,
                siteId = "GERMAN_CEMENT",
                kitchenId = "KITCHEN_RAYAN"
            ),
            MealItem(
                id = "MEAL_LZ04",
                code = "LZ-04",
                nameAr = "وجبة جافة للورديات الليلية والسفريات",
                nameEn = "Night Shift Dry Provision Meal",
                category = MealCategory.DRY_MEAL,
                ingredientsAr = "علبة تونة فاخرة، جبنة مثلثات، بسكويت مالح، علبة عصير طبيعي 250مل، خبز فينو معبأ آلياً، تفاحة",
                ingredientsEn = "Tuna chunk can, portion cheese, crackers, pure juice, sealed bread, fresh apple",
                portionSize = "450 جم",
                calories = 580,
                approvedRetailPrice = 65.0,
                internalCostPrice = 41.0,
                siteId = "ALL",
                kitchenId = "KITCHEN_BADR"
            ),
            MealItem(
                id = "MEAL_LZ05",
                code = "LZ-05",
                nameAr = "سمك فيليه قشر بياض مشوي مع أرز صيادية وسلطة خضراء",
                nameEn = "Grilled Fish Fillet with Sayadia Rice & Fresh Salad",
                category = MealCategory.LUNCH,
                ingredientsAr = "فيليه قشر بياض متبل بالكمون والليمون، أرز صيادية بني، سلطة خضراء بصلصة زيت الزيتون",
                ingredientsEn = "Nile fish fillet, cumin, lemon, brown rice, fresh olive oil garden salad",
                portionSize = "500 جم",
                calories = 610,
                approvedRetailPrice = 140.0,
                internalCostPrice = 88.0,
                siteId = "ALL",
                kitchenId = "KITCHEN_HANGER"
            ),
            MealItem(
                id = "MEAL_SL04",
                code = "SL-04",
                nameAr = "سلطة يونانية بالجبن الفيتا وزيت الزيتون البكر",
                nameEn = "Greek Feta Salad with Extra Virgin Olive Oil",
                category = MealCategory.SPECIAL,
                ingredientsAr = "خس كابوتشا، خيار، طماطم شيري، جبنة فيتا، زيتون كالاماتا، زعتر بري، زيت زيتون بكر ممتاز",
                ingredientsEn = "Iceberg lettuce, cucumber, cherry tomato, feta, Kalamata olives, oregano",
                portionSize = "260 جم",
                calories = 290,
                approvedRetailPrice = 40.0,
                internalCostPrice = 22.0,
                siteId = "ROYAL_HOSPITAL",
                kitchenId = "KITCHEN_HANGER"
            ),
            MealItem(
                id = "MEAL_RH01",
                code = "RH-01",
                nameAr = "وجبة رعاية طبية — حمية خالية من الملح والدهون (مرضى القلب)",
                nameEn = "Cardiac Low-Sodium Clinical Diet Meal",
                category = MealCategory.PATIENT_DIET,
                ingredientsAr = "صدور دجاج مسلوقة متبلة بأعشاب طبيعية بدون ملح، شوربة خضار مشكلة طازجة، أرز أبيض مسلوق",
                ingredientsEn = "Boiled chicken breast, zero-salt herbs, fresh clear vegetable soup, steamed white rice",
                portionSize = "420 جم",
                calories = 410,
                approvedRetailPrice = 95.0,
                internalCostPrice = 58.0,
                siteId = "ROYAL_HOSPITAL",
                kitchenId = "KITCHEN_HANGER"
            ),
            MealItem(
                id = "MEAL_SH01",
                code = "ES-01",
                nameAr = "وجبة حمية سكري متوازنة النشويات",
                nameEn = "Diabetic Balanced Glycemic Diet Meal",
                category = MealCategory.PATIENT_DIET,
                ingredientsAr = "لحم بتلو مشوي على البخار، فاصوليا خضراء طازجة، سلطة ورقيات، برغل أسمر",
                ingredientsEn = "Steamed veal cut, fresh green beans, leafy green salad, whole bulgur",
                portionSize = "430 جم",
                calories = 460,
                approvedRetailPrice = 110.0,
                internalCostPrice = 69.0,
                siteId = "SHOROUK_HOSPITAL",
                kitchenId = "KITCHEN_BADR"
            )
        )
        dao.insertMeals(meals)

        // --- 6. Canteen Items (Section 10 - German Cement & sites) ---
        val canteenItems = listOf(
            CanteenItem("CAN_01", "C-01", "ساندوتش كبده إسكندراني بالخبز الفينو", "Alexandrian Liver Sandwich", "مأكولات سريعة", "Hot Snacks", 28.0, 16.0, "GERMAN_CEMENT", 85, "ساندوتش", "Sandwich"),
            CanteenItem("CAN_02", "C-02", "ساندوتش حواوشي بلدي مقرمش", "Crispy Hawawshi Loaf", "مأكولات سريعة", "Hot Snacks", 35.0, 21.0, "GERMAN_CEMENT", 60, "رغيف", "Loaf"),
            CanteenItem("CAN_03", "C-03", "شاي كرك بالحليب والهيل والزعفران", "Karak Milk Tea with Cardamom", "مشروبات ساخنة", "Hot Drinks", 12.0, 4.5, "GERMAN_CEMENT", 300, "كوب", "Cup"),
            CanteenItem("CAN_04", "C-04", "قهوة تركي بن برازيلي محوج", "Turkish Coffee Medium Blend", "مشروبات ساخنة", "Hot Drinks", 18.0, 7.0, "GERMAN_CEMENT", 250, "فنجان", "Cup"),
            CanteenItem("CAN_05", "C-05", "عصير قصب طازج مثلج معبأ", "Chilled Fresh Sugarcane Juice", "مشروبات باردة", "Cold Drinks", 15.0, 6.0, "GERMAN_CEMENT", 120, "زجاجة", "Bottle"),
            CanteenItem("CAN_06", "C-06", "بار طاقة بالشوفان والعسل والمكسرات", "Granola Energy Bar with Honey", "وجبات خفيفة وسناكس", "Snacks", 22.0, 13.0, "GERMAN_CEMENT", 150, "قطعة", "Bar"),
            CanteenItem("CAN_07", "C-07", "مياه معدنية طبيعية 600 مل", "Natural Mineral Water 600ml", "مشروبات باردة", "Cold Drinks", 6.0, 2.8, "GERMAN_CEMENT", 500, "زجاجة", "Bottle")
        )
        dao.insertCanteenItems(canteenItems)

        // --- 7. Sample Initial Orders (Section 9) ---
        val orders = listOf(
            CateringOrder(
                id = "ORD_1001",
                orderNumber = "ORD-2026-1001",
                siteId = "GERMAN_CEMENT",
                siteNameAr = "الشركة الألمانية للأسمنت",
                siteNameEn = "German Cement Company",
                kitchenId = "KITCHEN_RAYAN",
                kitchenNameAr = "مطبخ الريان — اللاهون",
                orderDate = "2026-11-10",
                deliveryTime = "12:30 م (وردية الغداء)",
                mealDetailsSummary = "800 وجبة LZ-02 كفتة مشوية + 400 وجبة LZ-01 دجاج",
                quantity = 1200,
                totalAmount = 150000.0,
                status = OrderLifecycle.IN_PREPARATION,
                createdBy = "م. شريف عادل"
            ),
            CateringOrder(
                id = "ORD_1002",
                orderNumber = "ORD-2026-1002",
                siteId = "ROYAL_HOSPITAL",
                siteNameAr = "مستشفى رويال",
                siteNameEn = "Royal Hospital",
                kitchenId = "KITCHEN_HANGER",
                kitchenNameAr = "مطبخ هنجر استيشن",
                orderDate = "2026-11-10",
                deliveryTime = "01:00 م (وجبات المرضى والطاقم)",
                mealDetailsSummary = "450 وجبة RH-01 رعاية طبية + 350 وجبة LZ-01",
                quantity = 800,
                totalAmount = 83000.0,
                status = OrderLifecycle.READY,
                createdBy = "د. كريم سامي"
            ),
            CateringOrder(
                id = "ORD_1003",
                orderNumber = "ORD-2026-1003",
                siteId = "SHOROUK_HOSPITAL",
                siteNameAr = "مستشفى الشروق",
                siteNameEn = "El Shorouk Hospital",
                kitchenId = "KITCHEN_BADR",
                kitchenNameAr = "مطبخ مول العين — مدينة بدر",
                orderDate = "2026-11-09",
                deliveryTime = "12:00 م",
                mealDetailsSummary = "380 وجبة ES-01 حميات + 320 وجبة LZ-05 فيليه سمك",
                quantity = 700,
                totalAmount = 86600.0,
                status = OrderLifecycle.DELIVERED,
                createdBy = "أ. هدى منصور"
            )
        )
        orders.forEach { dao.insertOrder(it) }

        // --- 8. Central & Site Inventories (Section 13) ---
        val inventory = listOf(
            InventoryItem("INV_01", "RM-MEAT-01", "لحم بقري بلدي مبرد درجة أولى", "Chilled Local Beef Grade A", "لحوم ودواجن", "Meat & Poultry", "كجم", "KITCHEN_HANGER", "مطبخ هنجر استيشن", 850.0, 200.0, 320.0, "شركة مزارع النيل للإنتاج الحيواني", "2026-11-08"),
            InventoryItem("INV_02", "RM-CHICK-01", "دجاج مبرد طازج وزن 1100-1200 جم", "Fresh Chilled Whole Chicken", "لحوم ودواجن", "Meat & Poultry", "دجاجة", "KITCHEN_RAYAN", "مطبخ الريان", 1400.0, 300.0, 110.0, "شركة الوادي للدواجن", "2026-11-09"),
            InventoryItem("INV_03", "RM-RICE-01", "أرز بسمتي هندي سيلا ذهبي حبة طويلة", "Golden Sella Basmati Rice", "بقالة جافة", "Dry Goods", "شيكارة 25كجم", "CENTRAL", "المستودع اللوجستي المركزي", 420.0, 50.0, 1750.0, "مجموعة النعيم للاستيراد والتوزيع", "2026-11-05"),
            InventoryItem("INV_04", "RM-OIL-01", "زيت دوار الشمس نقي ممتاز للأغذية", "Pure Sunflower Cooking Oil", "بقالة جافة", "Dry Goods", "كرتونة 12لتر", "KITCHEN_BADR", "مطبخ مول العين", 280.0, 40.0, 840.0, "شركة أرما للزيوت الغذائية", "2026-11-07"),
            InventoryItem("INV_05", "PKG-BOX-01", "علب وجبات مقسمة 3 عيون معزولة حرارياً", "3-Compartment Thermal Meal Boxes", "عبوات وتغليف", "Packaging", "كرتونة 500 قطعة", "KITCHEN_HANGER", "مطبخ هنجر استيشن", 65.0, 15.0, 950.0, "المتحدة لصناعات التعبئة والتغليف", "2026-11-04")
        )
        dao.insertInventory(inventory)

        // --- 9. Financial Records & Vouchers (Section 12) ---
        val finance = listOf(
            FinancialRecord("FIN_01", "INV-2026-8801", "REVENUE", "إيراد توريد وجبات", "Catering Supply Revenue", "الشركة الألمانية للأسمنت", 450000.0, "2026-11-01", "فاتورة مستحقات توريد إعاشة الوردية الأولى لنوفمبر", "November First Half Invoice", true, "أ. إبراهيم فؤاد"),
            FinancialRecord("FIN_02", "INV-2026-8802", "REVENUE", "إيراد توريد وجبات", "Catering Supply Revenue", "مستشفى رويال", 260000.0, "2026-11-02", "فاتورة تغذية المرضى والكوادر الطبية", "Hospital Patient & Staff Catering", true, "أ. إبراهيم فؤاد"),
            FinancialRecord("FIN_03", "REC-2026-4412", "RECEIPT_VOUCHER", "سند قبض شيك بنكي", "Bank Check Receipt", "مستشفى الشروق", 185000.0, "2026-11-04", "تحصيل الدفعة الدورية من مستحقات شهر أكتوبر", "October Settlement Payment", true, "أ. إبراهيم فؤاد"),
            FinancialRecord("FIN_04", "EXP-2026-1209", "OPERATIONAL_EXPENSE", "مصروف تشغيل وخامات", "Raw Material Purchase Expense", "مطبخ هنجر استيشن", 95000.0, "2026-11-06", "سداد فاتورة توريدات اللحوم الطازجة الأسبوعية", "Weekly Meat Supply Payment", true, "أ. إبراهيم فؤاد")
        )
        finance.forEach { dao.insertFinancialRecord(it) }

        // --- 10. Commercial Quotations (Section 11) ---
        val quotes = listOf(
            QuotationProposal(
                id = "QUOTE_01",
                refCode = "CE-OFFER-2026-081",
                clientNameAr = "شركة نجوى تكنولوجي للبرمجيات",
                clientNameEn = "Nagwa Technology Ltd",
                serviceTypeAr = "إعاشة يومية متكاملة وغداء ساخن وبوفيه صحي",
                serviceTypeEn = "Full Corporate Lunch & Healthy Buffet",
                beneficiariesCount = 650,
                mealsPerDay = 650,
                agreedMealRate = 120.0,
                monthlyTotalRevenue = 1872000.0,
                internalCostEstimate = 1170000.0,
                projectedProfitMarginPct = 37.5,
                validityDays = 30,
                paymentTermsAr = "سداد دوري كل 15 يوماً بشيك مصرفي معتمد",
                paymentTermsEn = "Bi-weekly payment via certified corporate cheque",
                statusAr = "قيد المراجعة لدى العميل",
                dateCreated = "2026-11-05"
            ),
            QuotationProposal(
                id = "QUOTE_02",
                refCode = "CE-OFFER-2026-082",
                clientNameAr = "مصنع يونيماك للصناعات الدوائية",
                clientNameEn = "Unimac Pharmaceutical Industries",
                serviceTypeAr = "إعاشة طبية صناعية مطابقة لمواصفات الهايجين الدولي",
                serviceTypeEn = "Industrial Sterile Food Service",
                beneficiariesCount = 500,
                mealsPerDay = 500,
                agreedMealRate = 105.0,
                monthlyTotalRevenue = 1365000.0,
                internalCostEstimate = 858000.0,
                projectedProfitMarginPct = 37.1,
                validityDays = 45,
                paymentTermsAr = "سداد شهري بعد اعتماد فواتير التوريد",
                paymentTermsEn = "Monthly billing upon acceptance",
                statusAr = "تمت الموافقة المبدئية",
                dateCreated = "2026-11-07"
            )
        )
        quotes.forEach { dao.insertQuotation(it) }

        // --- 11. Marketing Coupons & Cashback (Section 16) ---
        val coupons = listOf(
            CouponItem(
                id = "CPN_01",
                code = "CE-WELCOME15",
                titleAr = "كوبون الترحيب بكاترينج مصر",
                titleEn = "Catering Egypt Welcome Voucher",
                descriptionAr = "خصم 15% على جميع طلبات الوجبات الخاصة وسناكس الكنتين",
                descriptionEn = "15% off all special meal orders and canteen snacks",
                discountPercent = 15,
                applicableSites = "ALL",
                validFrom = "2026-11-01",
                validUntil = "2026-12-31",
                termsAr = "صالح للاستخدام لجميع العاملين والعملاء التابعين للشركة الوطنية اللوجستية.",
                termsEn = "Valid for all affiliated employees and client staff."
            ),
            CouponItem(
                id = "CPN_02",
                code = "NAT-LOG10",
                titleAr = "كوبون عملاء الوطنية اللوجستية المعتمدين",
                titleEn = "National Logistics Client Privilege",
                descriptionAr = "خصم 10% إضافي وكاش باك 5% على الفواتير",
                descriptionEn = "10% direct discount plus 5% verified cashback",
                discountPercent = 10,
                applicableSites = "GERMAN_CEMENT,ROYAL_HOSPITAL",
                validFrom = "2026-11-05",
                validUntil = "2027-01-31",
                termsAr = "يطبق عند الشراء من الكنتين أو طلب وجبات الورديات الإضافية.",
                termsEn = "Applicable at on-site canteens and extra shift meals."
            ),
            CouponItem(
                id = "CPN_03",
                code = "EGYPT-SURPRISE",
                titleAr = "مفاجآت كاترينج مصر الأسبوعية",
                titleEn = "Weekly Catering Egypt Surprise",
                descriptionAr = "قسيمة بقيمة 25 جنيه مجانية على وجبة الغداء الساخنة",
                descriptionEn = "25 EGP voucher applied towards hot lunch",
                discountPercent = 0,
                discountFixedAmount = 25.0,
                applicableSites = "ALL",
                validFrom = "2026-11-08",
                validUntil = "2026-11-30",
                termsAr = "تستخدم لمرة واحدة أسبوعياً لكل رقم وظيفي معتمد.",
                termsEn = "One-time weekly use per verified employee badge."
            )
        )
        dao.insertCoupons(coupons)

        // --- 12. Seasonal Offers (Section 17) ---
        val seasonal = listOf(
            SeasonalOffer(
                id = "SEAS_01",
                titleAr = "كرتونة الخير الرمضانية — العائلية الفاخرة",
                titleEn = "Ramadan Premium Family Supply Box",
                subtitleAr = "الوزن الصافي 16 كجم خامات فاخرة نخب أول",
                subtitleEn = "Net Weight 16 kg Grade A essentials",
                descriptionAr = "تحتوي على: 5 كجم أرز فاخر، 3 كجم سكر، 2 زجاجة زيت ذرة، 2 كجم مكرونة، 1 كجم سمن طبيعي، 1 كجم بلح أسواني، فول وتوابل وشاي ممتاز.",
                descriptionEn = "Contains: 5kg rice, 3kg sugar, 2 corn oils, 2kg pasta, ghee, dates, beans, tea.",
                priceEgp = 580.0
            ),
            SeasonalOffer(
                id = "SEAS_02",
                titleAr = "شنطة رمضان الخيرية السريعة",
                titleEn = "Ramadan Fast Relief Food Bag",
                subtitleAr = "الوزن الصافي 8.5 كجم جاهزة للتوزيع الفوري",
                subtitleEn = "Net Weight 8.5 kg ready for distribution",
                descriptionAr = "تحتوي على: 2 كجم أرز، 2 كجم سكر، 1 كجم مكرونة، زجاجة زيت، نصف كجم سمن، نصف كجم فول، شاي وتمر.",
                descriptionEn = "Rice, sugar, pasta, cooking oil, ghee, fava beans, tea, dates.",
                priceEgp = 320.0
            ),
            SeasonalOffer(
                id = "SEAS_03",
                titleAr = "علبة كحك وبسكويت العيد بالسمن البلدي الفاخر",
                titleEn = "Eid Luxury Kahk & Biscuits Tin Box",
                subtitleAr = "تشكيلة كحك بالمكسرات والغريبة والبيتي فور الفاخر",
                subtitleEn = "Nut Kahk, Ghorayeba & French Petit Four",
                descriptionAr = "مصنوعة من دقيق فاخر وسمن بلدي طبيعي 100% ومكسرات عين جمل وفستق حلبي، مغلفة في علبة معدنية فاخرة مضادة للرطوبة.",
                descriptionEn = "100% pure butter ghee, walnuts, pistachios, moisture-sealed gift tin.",
                priceEgp = 420.0
            ),
            SeasonalOffer(
                id = "SEAS_04",
                titleAr = "صكوك ولحوم عيد الأضحى المبارك البلدي",
                titleEn = "Eid Al-Adha Fresh Local Meat Shares",
                subtitleAr = "لحم بقري بلدي مشفى ومقطع ومجمد تحت إشراف بيطري",
                subtitleEn = "Veterinary inspected boneless beef packs",
                descriptionAr = "أضحية بلدية مطابقة للشريعة الإسلامية، معبأة في أكياس مفرغة من الهواء ومحفوظة في سيارات التبريد اللوجستية حتى التسليم.",
                descriptionEn = "Fresh halal local cattle, vacuum sealed and distributed in refrigerated trucks.",
                priceEgp = 450.0
            ),
            SeasonalOffer(
                id = "SEAS_05",
                titleAr = "تمور رمضان الملكية الفاخرة",
                titleEn = "Royal Premium Ramadan Dates",
                subtitleAr = "تمر مجدول وصقعي من واحات سيوة والوادي الجديد",
                subtitleEn = "Medjool & Sagai organic desert oasis dates",
                descriptionAr = "تمور عضوية عالية الجودة معبأة بطرق الهايجين الآلي، مخصصة للمؤسسات ومواقع العمل والإفطار الجماعي.",
                descriptionEn = "Certified organic dates packed mechanically for institutional catering.",
                priceEgp = 180.0
            ),
            SeasonalOffer(
                id = "SEAS_06",
                titleAr = "علبة حلاوة المولد النبوي الشريف الفاخرة",
                titleEn = "Prophet Birthday Luxury Sweets Box",
                subtitleAr = "تشكيلة الحمصية والفولية والسمسمية والملبن بالمكسرات",
                subtitleEn = "Traditional Sesame, Peanut & Pistachio Malban",
                descriptionAr = "حلويات موسمية تصنع في مطابخنا المركزية بعسل النحل الطبيعي وبدون مواد حافظة ضارة.",
                descriptionEn = "Made in our central kitchens with pure bee honey and no harmful additives.",
                priceEgp = 290.0
            )
        )
        dao.insertSeasonalOffers(seasonal)

        // --- 13. Quality & Hygiene Audits (Section 14) ---
        val quality = QualityAudit(
            id = "QA_2026_01",
            inspectionDate = "2026-11-09",
            inspectorName = "د. سارة عزمي",
            kitchenOrSiteName = "مطبخ هنجر استيشن المركزي",
            mealSampleChecked = "وجبة LZ-01 دجاج مشوي مع أرز بسمتي",
            temperatureCelsius = 74.5,
            sensoryScore = 5,
            packagingCompliance = true,
            nonConformityDetails = "لا توجد أي حالات عدم مطابقة",
            correctiveActionTaken = "المطابقة تامة مع معايير الأيزو لسلامة الغذاء",
            overallRatingAr = "ممتاز ومطابق للمواصفات العالمية",
            overallRatingEn = "Excellent & Compliant"
        )
        dao.insertQualityAudit(quality)

        val hygiene = HygieneChecklist(
            id = "HYG_2026_01",
            date = "2026-11-10",
            officerName = "د. سارة عزمي",
            targetFacility = "مطبخ الريان — غرب بني سويف",
            kitchenCleanlinessScore = 98,
            equipmentSanitizationScore = 97,
            staffPersonalHygieneScore = 99,
            coldStorageTempPassed = true,
            foodExpiryValidated = true,
            disinfectionLogUpdated = true,
            hazardAlerts = "الحرارة المسجلة بثلاجة التبريد: 2.8 مئوية (مطابق تماماً)",
            nextAuditDate = "2026-11-17"
        )
        dao.insertHygieneChecklist(hygiene)

        // --- 14. Support Tickets & Complaints (Section 15) ---
        val tickets = listOf(
            SupportTicket(
                id = "TCK_01",
                ticketNumber = "TCK-2026-041",
                siteId = "GERMAN_CEMENT",
                siteNameAr = "الشركة الألمانية للأسمنت",
                employeeNumber = "GC-4820",
                employeeName = "المهندس هاني زكي",
                complaintTypeAr = "اقتراح تنويع المنيو",
                complaintTypeEn = "Menu Variety Suggestion",
                description = "نرجو إضافة صنف شوربة العدس الساخنة ضمن وجبة إفطار الوردية الشتوية.",
                dateSubmitted = "2026-11-08",
                status = "IN_PROGRESS",
                statusAr = "قيد المعالجة والدراسة بالتشغيل",
                statusEn = "In Progress with Ops Chef",
                targetResolutionDate = "2026-11-12",
                resolutionNotes = "تم التنسيق مع شيف مطبخ الريان لإدراج الصنف أسبوعياً."
            ),
            SupportTicket(
                id = "TCK_02",
                ticketNumber = "TCK-2026-042",
                siteId = "ROYAL_HOSPITAL",
                siteNameAr = "مستشفى رويال",
                employeeNumber = "RH-1104",
                employeeName = "الأستاذة مروة سليم",
                complaintTypeAr = "ملاحظة توقيت التوريد",
                complaintTypeEn = "Delivery Timing Note",
                description = "وجبات الغداء لطاقم التمريض تأخرت 15 دقيقة بالأمس، نرجو الالتزام الصارم بالساعة الواحدة ظهراً.",
                dateSubmitted = "2026-11-09",
                status = "RESOLVED",
                statusAr = "تم الحل والمتابعة مع الحركة",
                statusEn = "Resolved with Logistics",
                targetResolutionDate = "2026-11-10",
                resolutionNotes = "تم تخصيص سيارة مبردة إضافية مسبقاً وتعديل خط السير اللوجستي."
            )
        )
        tickets.forEach { dao.insertTicket(it) }

        // --- 15. Annual Distribution & CSR Initiatives (Section 18) ---
        val csr = listOf(
            CsrInitiative(
                id = "CSR_01",
                entityNameAr = "الهلال الأحمر المصري",
                entityNameEn = "Egyptian Red Crescent",
                initiativeTitleAr = "حملات التوريد والإغاثة الغذائية السنوية",
                initiativeTitleEn = "Annual Food Relief & Supply Campaigns",
                campaignTypeAr = "وجبات جافة معلبة وكراتين إغاثة غذائية متكاملة",
                campaignTypeEn = "Emergency Dry Provisions & Relief Boxes",
                targetVolumeMeals = 50000,
                distributionScheduleAr = "جداول توريد ربع سنوية وطوارئ على مدار العام",
                distributionScheduleEn = "Quarterly and Emergency Schedules",
                notesAr = "ملف رسمي لتجهيز الوجبات المعيارية الجافة المطابقة للمواصفات الدولية."
            ),
            CsrInitiative(
                id = "CSR_02",
                entityNameAr = "مبادرة حياة كريمة",
                entityNameEn = "Haya Karima Presidential Initiative",
                initiativeTitleAr = "توريد وجبات الإطعام بقرى ومراكز الريف المصري",
                initiativeTitleEn = "Rural Egypt Sustenance Supply Programs",
                campaignTypeAr = "مطابخ مركزية متنقلة ووجبات ساخنة مطهوة يومياً",
                campaignTypeEn = "Mobile Central Kitchens & Daily Cooked Hot Meals",
                targetVolumeMeals = 120000,
                distributionScheduleAr = "مواسم شهر رمضان والأعياد وقوافل الإطعام الأسبوعية",
                distributionScheduleEn = "Ramadan, Eid & Weekly Aid Convoys",
                notesAr = "توفير الوجبات من مطبخ الريان باللاهون ومطبخ مدينة بدر لتغطية المحافظات."
            ),
            CsrInitiative(
                id = "CSR_03",
                entityNameAr = "مؤسسة الدكتور مصطفى محمود الخيرية",
                entityNameEn = "Dr. Mostafa Mahmoud Charitable Foundation",
                initiativeTitleAr = "برنامج مائدة الرحمن والوجبات الطازجة للأسر المستحقة",
                initiativeTitleEn = "Daily Fresh Community Meals for Families",
                campaignTypeAr = "وجبات لحوم ودواجن طازجة مع أرز وخضار مطهي",
                campaignTypeEn = "Fresh Meat & Poultry Cooked Meals",
                targetVolumeMeals = 35000,
                distributionScheduleAr = "توريد يومي ثابت عبر أسطول سيارات النقل الحراري",
                distributionScheduleEn = "Daily thermal transport fleet delivery",
                notesAr = "عقد توريد سنوي موثق بجداول تسليم دقيقة وفواتير معتمدة."
            )
        )
        dao.insertCsrInitiatives(csr)

        // --- 16. Chat / Announcement Messages (Section 19) ---
        val messages = listOf(
            ChatMessage(
                id = "MSG_01",
                senderName = "م. أحمد عبد الرحمن (رئيس مجلس الإدارة)",
                senderRole = "الإدارة العليا",
                channelOrSite = "ALL",
                messageText = "السادة مديري المطابخ المركزية والمواقع: نحيطكم علماً ببدء تطبيق الخطة التشغيلية لموسم التوريدات الجديد، مع الالتزام الصارم بأعلى معايير الهايجين والجودة.",
                timestamp = "09:00 ص",
                isUrgent = true
            ),
            ChatMessage(
                id = "MSG_02",
                senderName = "د. سارة عزمي (مسؤول الجودة)",
                senderRole = "توكيد الجودة",
                channelOrSite = "KITCHENS",
                messageText = "تم اعتماد درجات الحرارة القياسية لسيارات النقل اللوجستي لليوم (أقل من 4 درجات للمبرد وفوق 65 للساخن).",
                timestamp = "09:45 ص",
                isUrgent = false
            ),
            ChatMessage(
                id = "MSG_03",
                senderName = "شيف طارق حسني (مطبخ هنجر استيشن)",
                senderRole = "مدير المطبخ",
                channelOrSite = "ROYAL_HOSPITAL",
                messageText = "تم الانتهاء من طهي وتجهيز وجبات الرعاية لمرضى مستشفى رويال وجاري التحميل لخط التوزيع.",
                timestamp = "11:15 ص",
                isUrgent = false
            )
        )
        messages.forEach { dao.insertMessage(it) }
    }
}
