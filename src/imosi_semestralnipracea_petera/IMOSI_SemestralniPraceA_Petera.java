/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imosi_semestralnipracea_petera;

import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.math3.distribution.*;

public class IMOSI_SemestralniPraceA_Petera {

    //all elements measured in milimeters
    private static double p1;
    private static double p2;
    private static double b;
    private static int edgespace;
    static double divergence;
    static int total;
    static double cumulativeAverage;
    static double previousAverage;
    static int successful;
    static int unsuccessful;
    static int price;
    
    public static void main(String[] args) throws IOException {
        b = 30;
        p1 = 2;
        p2 = 1.5;
        edgespace = 2;
        divergence = 0.001;
        cumulativeAverage = Double.MAX_VALUE;
        previousAverage = Double.MIN_VALUE;
        total = 0;
        successful = 0;
        unsuccessful = 0;
        price = 5;
        
        FileWriter writer = new FileWriter("data.csv");
        writer.append("Iteration");
        writer.append(';');
        writer.append("Cumulative 1");
        writer.append(';');
        writer.append("Price 1");
        writer.append('\n');
        
        while (Math.abs(previousAverage - cumulativeAverage) > divergence) {
            //Předání hodnoty aktuálního průměru do proměnné předchozí průměr
            
            previousAverage = cumulativeAverage;
            
            total++;
            
            NormalDistribution nd1 = new NormalDistribution(7, .4);
            UniformRealDistribution urd1 = new UniformRealDistribution(9.1, 9.9);
            TriangularDistribution td = new TriangularDistribution(7.6, 8.0, 8.2);
            NormalDistribution nd2 = new NormalDistribution(6.5, 0.45);
            
            double sum = nd1.sample() + urd1.sample() + (td.sample() - p1 - p2) + nd2.sample() + edgespace;
            
            //Kontrolování rozměrů
            
            if (sum > b) {
                unsuccessful++;
            } else {
                successful++;
            }
            
            //Jestliže je hodnota neúspěšných pokusů větší než 0, vypočítá se
            //průměr neúspěšných
            
            //Jinak se do celkového průměru hodí random hodnota, aby se mohlo
            //iterovat dále, jelikož by třeba po dvou cyklech došlo v obou
            //proměnných ke stejným hodnotám a cyklus by předčasně skončil
            
            if (unsuccessful != 0) {
                cumulativeAverage = (double)unsuccessful/total;
            } else {
                cumulativeAverage = Math.random()*1000;
            }
            
            writer.append(String.valueOf(total));
            writer.append(';');
            
            if (unsuccessful == 0) {
                writer.append(String.valueOf(0));
            } else {
                String helperString = String.valueOf(cumulativeAverage);
                writer.append(helperString.replace('.', ','));
            }
            
            writer.append(';');
            writer.append(String.valueOf(unsuccessful*price));
            writer.append('\n');

        }
        
        writer.flush();
        writer.close();
    }
    
}