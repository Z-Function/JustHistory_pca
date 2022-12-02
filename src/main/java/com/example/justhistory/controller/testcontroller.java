package com.example.justhistory.controller;

import jakarta.servlet.http.HttpServletRequest;
import javaInvokePcaDll.invokePca;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@RestController
@RequestMapping("/test")
public class testcontroller {

    @PostMapping("/api/getStatistics")
    public  Object getStatistics(HttpServletRequest request) throws IOException, UnsupportedEncodingException {
        HashMap<String, Object> result = new HashMap<String, Object>();
        invokePca pca=new invokePca();
        double[][] data = new double[200][1];
        for (int i=0;i<200;i++)
            data[i][0] = 0.67;
        double[][] new_data = new double[300][1];
        for (int i=0;i<300;i++)
            new_data[i][0] = 0.68;
        double percent = 0.9;
        double faultNumber = 3;
        double[] deviceMax;
        double maxNum = 8000000;
        deviceMax = new double[1];
        for (int i = 0; i < 1; i++)
            deviceMax[i] = maxNum;
        double [] Res;
        int stdRow = 200;
        int stdCol = 1;
        int gap = 300;
        int checkCol=1;
        double[] res= pca.getStatistics(data,new_data,percent,faultNumber,deviceMax,stdRow,stdCol,gap,checkCol);
        result.put("code",200);
        return result;
    }

}
