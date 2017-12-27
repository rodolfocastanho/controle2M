package br.com.rodolfocastanho.controle2M;

import br.com.rodolfocastanho.controle2M.model.reports.TesteChartModel;
import org.junit.Before;

public class ModelTest {

    public static void main(String[] args){
        TesteChartModel dadosChart = new TesteChartModel(3.0,3.0, 3.0, 3.0, 2.0, 3.0);

        double[] dadosChart2 = new double[]{3.0, 3.0, 3.0, 3.0, 2.0, 3.0};
        System.out.println(dadosChart2);

        int x = 0;
        int y = 2;
        System.out.println(x/y);

        double z = 23.4945;
        System.out.println(Math.round(z));


        double[] dadosChartx = new double[6];
        dadosChartx[0] = 3.0;
        dadosChartx[1] = 3.0;
        dadosChartx[2] = 3.0;
        dadosChartx[3] = 3.0;
        dadosChartx[4] = 2.0;
        dadosChartx[5] = 1.0;

        String dadosCharty = dadosChartx.clone().toString();
        System.out.println(dadosChartx);
        System.out.println(dadosCharty);

    }

}
