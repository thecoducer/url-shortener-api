package com.thecoducer.shortenurl.service;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class UniqueShortKeyGeneratorService {

    public String generateUniqueShortKey(String url) {
        return shortenBase64EncodedString(getBase64EncodedString(getMd5Hash(url)));
    }

    private String getMd5Hash(String url) {
        return DigestUtils.md5Hex(url).toUpperCase();
    }

    private String getBase64EncodedString(String md5Hash) {
        Base64 base64 = new Base64();
        return new String(base64.encode(md5Hash.getBytes()));
    }

    private String shortenBase64EncodedString(String encodedString) {
        encodedString = encodedString.substring(0, 40);
        int length = encodedString.length();
        char[] charArray = encodedString.toCharArray();

        // swap characters randomly
        for(int i=0;i<1000;i++) {
            int a = (int) (Math.random() * length);
            int b = (int) (Math.random() * length);

            char temp = charArray[a];
            charArray[a] = charArray[b];
            charArray[b] = temp;
        }

        return new String(charArray).substring(0, 8);
    }
}
