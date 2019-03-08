package project.com.maktab.onlinemarket.utils;

import java.util.List;

public class Utils {

    public static String getRemovedBracketArray(List<String> list){
        String sendList = "";
        for(int i = 0;i<list.size();i++){
            if(i==list.size()-1)
                sendList += list.get(i);
            else
                sendList += list.get(i) + ", ";
        }
        return sendList;
    }

}
