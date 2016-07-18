
package com.mahmoud.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MyFollowersModel {

    @SerializedName("users")
    @Expose
    private List<User> users = new ArrayList<User>();
    @SerializedName("next_cursor_str")
    @Expose
    private String nextCursorStr;

    /**
     * 
     * @return
     *     The users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * 
     * @param users
     *     The users
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * 
     * @return
     *     The nextCursorStr
     */
    public String getNextCursorStr() {
        return nextCursorStr;
    }

    /**
     * 
     * @param nextCursorStr
     *     The next_cursor_str
     */
    public void setNextCursorStr(String nextCursorStr) {
        this.nextCursorStr = nextCursorStr;
    }

}
