package com.ll;

import java.util.Arrays;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

public class Calc {
    public static int run(String s) {
        if(s.contains(" ") == false) return Integer.parseInt(s);

        boolean needToMulti = s.contains(" * "); //특정 문자 하나를 포함하는지의 여부
        boolean needToPlus = s.contains(" + ") || s.contains(" - ");

        boolean needToCompound = needToMulti && needToPlus;

        s = s.replaceAll("- ", "+ -");
        int sum = 0;
        int multi = 1;
        if(needToCompound){
            //s = s.replaceAll("- ", "+ -");
            String[] compound_bits = s.split(" \\+ "); //

            String output_bits = Arrays.stream(compound_bits)
                    .mapToInt(e -> Calc.run(e))
                    .mapToObj(e -> e + "")
                    .collect(Collectors.joining( " + "));

            return run(output_bits);
            //return Integer.parseInt(compound_bits[0])+Calc.run(compound_bits[1]);
        }
        else if(needToPlus){
            //s = s.replaceAll("- ", "+ -");
            String[] bits = s.split(" \\+ ");


            for(int i = 0 ; i < bits.length ; i++){
                sum += Integer.parseInt(bits[i]);

            }
            return sum;

        }

        else if(needToMulti){
            //s = s.replaceAll("- ", "* -");
            String[] bits = s.split(" \\* ");

            for(int i = 0 ; i < bits.length ; i++){
                multi *= Integer.parseInt(bits[i]);
            }
            return multi;
        }

        throw new RuntimeException("올바른 게산식 쓰셈");
    }

}
