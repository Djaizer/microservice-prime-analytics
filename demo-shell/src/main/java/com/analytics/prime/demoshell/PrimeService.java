package com.analytics.prime.demoshell;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class PrimeService {

    private static List<Integer> primeNumbers;

    @Autowired
    ReportService reportService;

    public Integer getPrime(Integer number, Integer userId) {
        Integer prime = primeNumbers.get(number * 100 - 1);
        reportService.setDataToReport(userId, String.format("User name/request/response\n User %d /%d/%d", userId, number, prime));
        return prime;
    }

    public PrimeService() {
        primeNumbers = primeNumbersBruteForce(100000);

    }

    private static List<Integer> primeNumbersBruteForce(int n) {
        List<Integer> primeNumbers = new LinkedList<>();
        for (int i = 2; i <= n; i++) {
            if (isPrimeBruteForce(i)) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }

    private static boolean isPrimeBruteForce(int number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }


}
