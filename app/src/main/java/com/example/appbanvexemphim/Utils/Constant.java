package com.example.appbanvexemphim.Utils;

import com.example.appbanvexemphim.Model.Service;
import com.example.appbanvexemphim.Model.Ticket;
import com.example.appbanvexemphim.Model.User;

import java.util.ArrayList;

public class Constant {
    public static String MY_SHARED_PRE = "MY_SHARED_PREFERENCES";
    public static String ImageFolderSuffix = "images/";
    public static User user = null;
    public static enum userRole{
        ADMIN,
        CUSTOMER
    }
    public static enum payment{
        ZALOPAY,
        MOMO
    }
    public static String getDayOfWeek(int day) {
        switch (day) {
            case 1:
                return "Chủ nhât";
            case 2:
                return "Thứ 2";
            case 3:
                return "Thứ 3";
            case 4:
                return "Thứ 4";
            case 5:
                return "Thứ 5";
            case 6:
                return "Thứ 6";
            case 7:
                return "Thứ 7";
            default:
                return "Ngày lạ";
        }
    }

    public static Float calculateTotal(ArrayList<Ticket> tickets, Service service) {
        Float result = (float) 0L;
        for (Ticket t : tickets) {
            result += t.getPrice();
        }
        if (service != null)
            result += service.getPrice();
        return result;
    }

    public static String calculateSeatSelected(ArrayList<Ticket> tickets) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < tickets.size(); i++) {
            stringBuilder.append(tickets.get(i).getSeatName());
            if (i < tickets.size() - 1) {
                stringBuilder.append(", ");
            }
        }

        String result = stringBuilder.toString();
        return result;
    }
}
