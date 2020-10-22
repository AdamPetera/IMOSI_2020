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
        
        FileWriter writer = new FileWriter("data.csv");
        writer.append("Pokus");
        writer.append(';');
        writer.append("Prumer");
        writer.append('\n');
        
        while (Math.abs(previousAverage - cumulativeAverage) > divergence) {
            //Předání hodnoty aktuálního průměru do proměnné předchozí průměr
            
            previousAverage = cumulativeAverage;
            
            total++;
            
            NormalDistribution nd1 = new NormalDistribution(7, .4);
            UniformRealDistribution urd1 = new UniformRealDistribution(9.1, 9.9);
            TriangularDistribution td = new TriangularDistribution(7.6, 8.0, 8.4);
            NormalDistribution nd2 = new NormalDistribution(6.5, 0.45);
            
            double sum = nd1.sample() + urd1.sample() + (td.sample() - p1 - p2) + nd2.sample() + edgespace;
            
            //Kontrolování rozměrů
            double helper;
            
            if (sum > b) {
                unsuccessful++;
                helper = 1;
            } else {
                successful++; 
                helper = 0;
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
            /*writer.append(String.valueOf(helper));
            writer.append(';');*/
            if (unsuccessful == 0) {
                writer.append(String.valueOf(0));
            } else {
                writer.append(String.valueOf(cumulativeAverage));
            }
            writer.append('\n');
            
            System.out.println("Successful:" + successful);
            System.out.println("Unsuccessful: " + unsuccessful);
            System.out.println("Cumulative average: " + cumulativeAverage);
            System.out.println("Difference: " + (Math.abs(previousAverage-cumulativeAverage)));
            System.out.println("Price for unsuccessful pieces: " + (unsuccessful*5));

        }
        
        writer.flush();
        writer.close();
    }
    
}