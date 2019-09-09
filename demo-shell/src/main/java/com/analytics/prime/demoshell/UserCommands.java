package com.analytics.prime.demoshell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.HashMap;
import java.util.Map;

@ShellComponent
public class UserCommands {
    private static Integer userId = 0;
    private static String currentUserName = "User0";

    Map<Integer, String> users = new HashMap<>();

    @Autowired
    KafkaService kafkaService;


    @ShellMethod("Add two integers together.")
    public int add(int a, int b) {
        return a + b;
    }


    @ShellMethod("Add new user.")
    public String user() {
        userId++;
        currentUserName = "User" + userId;
        users.put(userId, currentUserName);
        return "User is " + users.get(userId) + " is created";
    }

    @ShellMethod("Switch to user")
    public String setuser(int userID) {
        currentUserName = "User" + userID;
        users.put(userID, currentUserName);
        //   kafkaService.setCurrentUser(userId);
        return "Your Current User is: " + currentUserName;
    }

    @ShellMethod("Remove existed user.")
    public String remove(int userID) {
        if (users.containsKey(userID)) {
            users.remove(userID);
            return "User is " + userID + " is deleted";
        }
        return "There is no such User as " + userId;
    }


    @ShellMethod("Send a number, to get prime number.")
    public String number(int number) {
        kafkaService.sendNumber(userId, number);
        return "\n";
    }

    @ShellMethod("Ask server to generate a report")
    public String report() {
        if (kafkaService.isUserNotExist(userId)) {
            return "Send a number first";
        }
        kafkaService.getReport(userId);
        return "\n";
    }

    @ShellMethod("Ask server of a report status.")
    public String status() {
        if (kafkaService.isUserNotExist(userId)) {
            return "Send a number first";
        }
        kafkaService.getReportStatus(userId);
        return "\n";
    }

}
