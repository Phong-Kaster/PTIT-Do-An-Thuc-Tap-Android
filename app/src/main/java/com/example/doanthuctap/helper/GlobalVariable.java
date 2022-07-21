package com.example.doanthuctap.helper;

import android.app.Application;

import com.example.doanthuctap.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Phong-Kaster
 * Class nay duoc dung de luu bien toan cuc trong do an nay
 * moi bien duoc liet ke duoi day co the truy cap o bat ki dau trong du an
 *
 * setter: ((GlobalVariable) this.getApplication()).setSomeVariable("foo");
 * getter: String s = ((MyApplication) this.getApplication()).getSomeVariable();
 */
public class GlobalVariable extends Application {
    private String accessToken;
    private User AuthUser;
    private final String SHARED_PREFERENCE_KEY = "doanthuctap";
    private String contentType = "application/x-www-form-urlencoded";
    private Map<String, String> headers;

    /*** GETTER & SETTER ***/
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public User getAuthUser() {
        return AuthUser;
    }

    public void setAuthUser(User authUser) {
        AuthUser = authUser;
    }

    public String getSharedReferenceKey() {
        return SHARED_PREFERENCE_KEY;
    }

    public String getContentType() {
        return contentType;
    }

    /***
     * @author Phong-Kaster
     *
     * this functions supports us establish a header which is used in a HTTP request
     *
     * @return
     */
    public Map<String, String> getHeaders() {

        this.headers = new HashMap<>();
        this.headers.put("Content-Type", contentType );
        this.headers.put("Authorization", accessToken);

        return headers;
    }
}
