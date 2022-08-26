package com.epam.esm.common;

import com.epam.esm.entity.GiftCertificate;

import java.util.HashMap;
import java.util.Map;

import static com.epam.esm.common.GiftCertificateConstant.*;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Checks whether the parameters have changed
 */
public class GiftCertificateHandler {

    private GiftCertificateHandler(){
    }

    public static Map<String, Object> defineChanges (GiftCertificate giftCertificate, GiftCertificate giftCertificateDB){
        Map<String, Object> result = new HashMap<>();
        if (isStringChanged(giftCertificateDB.getName(), giftCertificate.getName())) {
            result.put(NAME, giftCertificate.getName());
        }
        if (isStringChanged(giftCertificateDB.getDescription(), giftCertificate.getDescription())) {
            result.put(DESCRIPTION, giftCertificate.getDescription());
        }
        if (giftCertificate.getPrice() != null &&
                giftCertificateDB.getPrice().compareTo(giftCertificate.getPrice()) != 0) {
            result.put("price", giftCertificate.getPrice());
        }
        if (giftCertificate.getDuration() != null &&
                !giftCertificateDB.getDuration().equals(giftCertificate.getDuration())) {
            result.put("duration", giftCertificate.getDuration());
        }
        if (giftCertificate.getCreateDate() != null &&
                !giftCertificateDB.getCreateDate().equals(giftCertificate.getCreateDate())) {
            result.put(CREATE_DATE, giftCertificate.getCreateDate());
        }
        if (giftCertificate.getLastUpdateDate() != null &&
                !giftCertificateDB.getLastUpdateDate().equals(giftCertificate.getLastUpdateDate())) {
            result.put("lastUpdateDate", giftCertificate.getLastUpdateDate());
        }
        return result;
    }

    private static boolean isStringChanged(String dbField, String field) {
        return isNotEmpty(field) && dbField.equals(field);
    }
}
