package com.example.justhistory.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import javaInvokePcaDll.invokJson;
import javaInvokePcaDll.invokePca;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.function.DoubleToLongFunction;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/invok")
public class invokcontroller {
    @PostMapping("/api/getStatistics")
    public  Object getStatistics(HttpServletRequest data) throws IOException, UnsupportedEncodingException {

        data.setCharacterEncoding("UTF-8");         //返回页面防止出现中文乱码
        BufferedReader reader = new BufferedReader(new InputStreamReader(data.getInputStream()));//post方式传递读取字符流
        String jsonStr = null;
        StringBuilder result = new StringBuilder();
        try {
            while ((jsonStr = reader.readLine()) != null) {
                result.append(jsonStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        reader.close();// 关闭输入流
        JSONObject jsonObject = JSONObject.parseObject(result.toString()); // 取一个json转换为对象

        String old_data=jsonObject.getString("old_data");
        String[][] arr_old_data= JSON.parseObject(old_data,String[][].class);
        double[][] ds_old_data=new double[arr_old_data.length][arr_old_data[0].length];
        for(int j =0;j<arr_old_data.length;j++)
        {
            for(int i =0;i<arr_old_data[0].length;i++)
            {
                ds_old_data[j][i]=Double.valueOf(arr_old_data[j][i]);
            }
        }

        String new_data=jsonObject.getString("new_data");
        String[][] arr_new_data= JSON.parseObject(new_data,String[][].class);
        double[][] ds_new_data=new double[arr_new_data.length][arr_new_data[0].length];
        for(int j =0;j<arr_new_data.length;j++)
        {
            for(int i =0;i<arr_new_data[0].length;i++)
            {
                ds_new_data[j][i]=Double.valueOf(arr_new_data[j][i]);
            }
        }

        String percent=jsonObject.getString("percent");
        double ds_percent=Double.parseDouble(percent);

        String faultNumber=jsonObject.getString("faultNumber");
        double ds_faultNumber=Double.parseDouble(faultNumber);

        String deviceMax=jsonObject.getString("deviceMax");
        String[] arr_deviceMax=JSON.parseObject(deviceMax,String[].class);
        double[] ds_deviceMax=new double[arr_deviceMax.length];
        for(int i=0;i<ds_deviceMax.length;i++){
            ds_deviceMax[i]=new Double(arr_deviceMax[i]);
        }

        int stdRow=jsonObject.getInteger("stdRow");
        int stdCol=jsonObject.getInteger("stdCol");
        int gap=jsonObject.getInteger("gap");
        int checkCol=jsonObject.getInteger("checkCol");


        HashMap<String, Object> result1 = new HashMap<String, Object>();
        invokePca pca=new invokePca();
        double[] res=pca.getStatistics(ds_old_data,ds_new_data,ds_percent,ds_faultNumber,ds_deviceMax,stdRow,stdCol,gap,checkCol);
        result1.put("code",200);
        result1.put("data",res);
        return result1;
    }
    public static String fetchRequest2Str(HttpServletRequest request) {
        String reqStr= null;
        BufferedReader streamReader=null;
        try {
            streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            reqStr =responseStrBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(streamReader!=null){streamReader.close();}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return reqStr;
    }


}
