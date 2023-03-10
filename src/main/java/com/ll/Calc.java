package com.ll;

public class Calc {
    public static int run(String s) {

        boolean needToMulti = s.contains("*"); //특정 문자 하나를 포함하는지의 여부
        boolean needToPlus = !needToMulti;
        int sum = 0;
        int multi = 1;
        if(needToMulti){
            s = s.replaceAll("- ", "* -");
            String[] bits = s.split(" \\* ");

            for(int i = 0 ; i < bits.length ; i++){
                multi *= Integer.parseInt(bits[i]);
            }
            return multi;

        }
        else{
            s = s.replaceAll("- ", "+ -");
            String[] bits = s.split(" \\+ ");


            for(int i = 0 ; i < bits.length ; i++){
                sum += Integer.parseInt(bits[i]);

            }
            return sum;
        }

        //throw new RuntimeException("올바른 게산식 쓰셈");
    }

}
