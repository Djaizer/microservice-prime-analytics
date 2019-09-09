package com.analytics.prime.demoshell;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Component
public class ReportService {

    private Integer timeLeft;
    private static Map<Integer, String> data = new HashMap<>();

    public void setDataToReport(Integer userId, String report) {
        data.put(userId, report);
    }

    public String getStatus() {
        return timeLeft + " seconds";
    }


    public String getReport(Integer userId) {
        timeLeft = (int) (Math.random() * 10);
        IntStream.range(1, timeLeft).forEach(r ->
        {
            sleep(1000);
            timeLeft--;
        });
        return data.get(userId);
    }


    private static void sleep(int n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException ex) {
            // never mind
        }


    }

}
