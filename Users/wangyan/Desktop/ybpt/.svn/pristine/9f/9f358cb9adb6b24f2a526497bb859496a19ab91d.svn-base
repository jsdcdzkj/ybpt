package com.jsdc.ybpt.util;

import com.jsdc.ybpt.vo.ReconciliationType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 对账相关方法
 */
public class ReconciliationUtils {
    /**
     * 对账类型条件封装
     * @return
     */
    public static List<ReconciliationType> getReconciliationTypes() {
        List<ReconciliationType> reconciliationTypes = new ArrayList<>();
        //职工门诊(含血液特殊病)
        ReconciliationType r1 = new ReconciliationType("staff_mz","310", "'11','1101','1102','1103','1201','1202','1203','34','12','116014','126015'","'11','140104','140201','15','41','990602','139901','139902','139903'");
        reconciliationTypes.add(r1);
        //职工住院
        ReconciliationType r2 = new ReconciliationType("staff_zy","310", "'11','1101','1102','1103','1201','1202','1203','34','12','116014','126015'","'21'");
        reconciliationTypes.add(r2);
        //职工家床
        ReconciliationType r3 = new ReconciliationType("staff_jc","310", "'11','1101','1102','1103','1201','1202','1203','34','12','116014','126015'","'71'");
        reconciliationTypes.add(r3);
        //职工门诊单病种
        ReconciliationType r4 = new ReconciliationType("staff_dbz","310", "'11','1101','1102','1103','1201','1202','1203','34','12','116014','126015'","'990101'");
        reconciliationTypes.add(r4);
        //职工住院单病种（含日间手术）
        ReconciliationType r5 = new ReconciliationType("staff_dbz_day","310", "'11','1101','1102','1103','1201','1202','1203','34','12','116014','126015'","'26','28'");
        reconciliationTypes.add(r5);
        //职工生育
        ReconciliationType r6 = new ReconciliationType("staff_sy","510", "'11','1101','1102','1201','1202','116014','126015'","'52'");
        reconciliationTypes.add(r6);
        //职工住院按床日付费
        ReconciliationType r7 = new ReconciliationType("staff_zy_day","310", "'11','1101','1102','1103','1201','1202','1203','34','12','116014','126015'","'9934'");
        reconciliationTypes.add(r7);
        //居民门诊（含血液特殊病）
        ReconciliationType r8 = new ReconciliationType("resident_mz","390", "'14','1401','1402','1403','1404','15','1501','156002','16'","'11','140104','140201','15','41','990602','139901','139902','139903'");
        reconciliationTypes.add(r8);
        //居民住院（含家床）
        ReconciliationType r9 = new ReconciliationType("resident_zy","390", "'14','1401','1402','1403','1404','15','1501','156002','16'","'21','990301','71'");
        reconciliationTypes.add(r9);
        //居民门诊单病种
        ReconciliationType r10 = new ReconciliationType("resident_dbz","390", "'14','1401','1402','1403','1404','15','1501','156002','16'","'990101'");
        reconciliationTypes.add(r10);
        //居民住院单病种（含日间手术）
        ReconciliationType r11 = new ReconciliationType("resident_dbz_day","390", "'14','1401','1402','1403','1404','15','1501','156002','16'","'26','28'");
        reconciliationTypes.add(r11);
        //居民生育
        ReconciliationType r12 = new ReconciliationType("resident_sy","390", "'14','1401','1402','1403','1404','15','1501','156002','16'","'52'");
        reconciliationTypes.add(r12);
        //居民住院按床日付费
        ReconciliationType r13 = new ReconciliationType("resident_zy_day","390", "'14','1401','1402','1403','1404','15','1501','156002','16'","'9934'");
        reconciliationTypes.add(r13);
        //伤残军人门诊
        ReconciliationType r14 = new ReconciliationType("servicemen_mz","350", "'116014','126015'","'11','15','41'");
        reconciliationTypes.add(r14);
        //伤残军人住院
        ReconciliationType r15 = new ReconciliationType("servicemen_zy","350", "'116014','126015'","'21'");
        reconciliationTypes.add(r15);
        //离休门诊
        ReconciliationType r16 = new ReconciliationType("retire_mz","340", "'13','1300','136015','136019'","'11','15','41'");
        reconciliationTypes.add(r16);
        //离休住院
        ReconciliationType r17 = new ReconciliationType("retire_zy","340", "'13','1300','136015','136019'","'21'");
        reconciliationTypes.add(r17);
        //个人灵活就业人员生育
        ReconciliationType r18 = new ReconciliationType("personal_sy","310", "'1103','1203','116014','126015'","'52'");
        reconciliationTypes.add(r18);
        //职工新冠门诊
        ReconciliationType r19 = new ReconciliationType("staff_mz_covid19","310", "'11','1101','1102','1103','1201','1202','1203','34','12','116014','126015'","'1102'");
        reconciliationTypes.add(r19);
        //职工新冠住院
        ReconciliationType r20 = new ReconciliationType("staff_zy_covid19","310", "'11','1101','1102','1103','1201','1202','1203','34','12','116014','126015'","'2110'");
        reconciliationTypes.add(r20);
        //居民新冠门诊
        ReconciliationType r21 = new ReconciliationType("resident_mz_covid19","390", "'14','1401','1402','1403','1404','15','1501','156002','16'","'1102'");
        reconciliationTypes.add(r21);
        //居民新冠住院
        ReconciliationType r22 = new ReconciliationType("resident_zy_covid19","390", "'14','1401','1402','1403','1404','15','1501','156002','16'","'2110'");
        reconciliationTypes.add(r22);
        return reconciliationTypes;
    }


    /**
     * 异地对账类型条件封装
     * @return 3203
     */
    public static List<ReconciliationType> getReconciliationTypes_yd() {
        List<ReconciliationType> reconciliationTypes = new ArrayList<>();
        //职工门诊（异地）
        ReconciliationType r1 = new ReconciliationType("staff_mz","310", "'11','1101','1102','1103','1201','1202','1203','34','12'","'11','140104','140201','15','41','139901','139902','139903'");
        reconciliationTypes.add(r1);
        //职工住院（异地）
        ReconciliationType r2 = new ReconciliationType("staff_zy","310", "'11','1101','1102','1103','1201','1202','1203','34','12'","'21','23','25'");
        reconciliationTypes.add(r2);
        //居民门诊（异地）
        ReconciliationType r8 = new ReconciliationType("resident_mz","390", "'14','1402','1403','1404','1405','15','1501','16'","'11','140104','140201','15','41','139901','139902','139903'");
        reconciliationTypes.add(r8);
        //居民住院（含家床）（异地）
        ReconciliationType r9 = new ReconciliationType("resident_zy","390", "'14','1402','1403','1404','1405','15','1501','16'","'21','23','25'");
        reconciliationTypes.add(r9);
        //伤残军人门诊（异地）
        ReconciliationType r14 = new ReconciliationType("servicemen_mz","350", "'116014','126015'","'11','15','41'");
        reconciliationTypes.add(r14);
        //伤残军人住院（异地）
        ReconciliationType r15 = new ReconciliationType("servicemen_zy","350", "'116014','126015'","'21','23','25'");
        reconciliationTypes.add(r15);
        //职工新冠门诊（异地）
        ReconciliationType r19 = new ReconciliationType("staff_mz_covid19","310", "'11','1101','1102','1103','1201','1202','1203','34','12'","'1102'");
        reconciliationTypes.add(r19);
        //职工新冠住院（异地）
        ReconciliationType r20 = new ReconciliationType("staff_zy_covid19","310", "'11','1101','1102','1103','1201','1202','1203','34','12'","'2110'");
        reconciliationTypes.add(r20);
        //居民新冠门诊（异地）
        ReconciliationType r21 = new ReconciliationType("resident_mz_covid19","390", "'14','1402','1403','1404','1405','15','1501','16'","'1102'");
        reconciliationTypes.add(r21);
        //居民新冠住院（异地）
        ReconciliationType r22 = new ReconciliationType("resident_zy_covid19","390", "'14','1402','1403','1404','1405','15','1501','16'","'2110'");
        reconciliationTypes.add(r22);
        return reconciliationTypes;
    }
    /**
     * 对账类型条件封装
     * @return
     */
    public static HashMap getReconciliationTypeMap(){
        HashMap<String,ReconciliationType> hashMap = new HashMap();
        getReconciliationTypes().forEach(x->{
            hashMap.put(x.getType(),x);
        });
        return hashMap;
    }

    /**
     * 异地对账类型条件封装
     * @return
     */
    public static HashMap getReconciliationTypeMap_yd(){
        HashMap<String,ReconciliationType> hashMap = new HashMap();
        getReconciliationTypes_yd().forEach(x->{
            hashMap.put(x.getType(),x);
        });
        return hashMap;
    }
}
