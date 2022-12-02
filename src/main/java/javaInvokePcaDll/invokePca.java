package javaInvokePcaDll;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class invokePca {
    private native double[] pcaFaultDiagnosis(double[][] data, double[][] new_data, double percent, double faultNumber , double[] faultData, int row, int col, int new_row, int new_col);
    public double[] getStatistics(double[][] data, double[][] new_data, double percent, double faultNumber , double[] faultData, int row, int col, int new_row, int new_col)throws IOException, UnsupportedEncodingException {
        invokePca pca=new invokePca();
        System.load("C:\\Users\\YangJ\\AppData\\Local\\Temp\\PCA_Dll20221125.dll");
        try
        {
            double[] res = pca.pcaFaultDiagnosis(data, new_data,  percent,  faultNumber , faultData,  row,  col,  new_row,  new_col);
            return res;
        }
        catch (Exception exception)
        {
            return new double[2];
        }

    }
}
