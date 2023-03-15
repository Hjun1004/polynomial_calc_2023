package com.ll;

import java.util.Arrays;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

public class Calc {
    public static int run(String exp) {
        exp = exp.trim();
        exp = stripOuterBrackets(exp);

        // 단일항이 입력되면 바로 리턴
        if(exp.contains(" ") == false) return Integer.parseInt(exp);

        boolean needToMulti = exp.contains(" * "); //특정 문자 하나를 포함하는지의 여부
        boolean needToSplit = exp.contains("(") || exp.contains(")");
        boolean needToPlus = exp.contains(" + ") || exp.contains(" - ");


        boolean needToCompound = needToMulti && needToPlus;

        int sum = 0;
        int multi = 1;

        if(needToSplit) {
            int splitPointIndex = findSplintPointIndex(exp);


            String firstExp = exp.substring(0, splitPointIndex);
            String secondExp = exp.substring(splitPointIndex+1);
            char operationCode = exp.charAt(splitPointIndex);
            exp = Calc.run(firstExp) + " " + operationCode + " " + Calc.run(secondExp);
            return run(exp);


        }

        else if(needToCompound) {
            exp = exp.replaceAll("- ", "+ -");
            String[] compound_bits = exp.split(" \\+ "); //

            String newExp = Arrays.stream(compound_bits) // 안에 들어있는 각각의 요소를 스트림으로 바꾼다.
                    .mapToInt(e -> Calc.run(e)) // 각 요소 문자열을 Calc.rue(e) 에 넣는다. run()함수는 문자열을 매개변수로 받기 때문에 가능
                    .mapToObj(e -> e + "") // run에서 return받은 값들을 ""로 감싸서 문자열들로 바꾸고
                    .collect(Collectors.joining( " + ")); // 그 문자열들을 " + " 더하기를 붙이면서 하나하나 조합해 최종 문자열을 만든다.

            return run(newExp);
            //return Integer.parseInt(compound_bits[0])+Calc.run(compound_bits[1]);
        }

        else if(needToPlus){
            exp = exp.replaceAll("- ", "+ -");
            String[] bits = exp.split(" \\+ ");


            for(int i = 0 ; i < bits.length ; i++){
                sum += Integer.parseInt(bits[i]);

            }
            return sum;

        }

        else if(needToMulti){
            exp = exp.replaceAll("- ", "* -");
            String[] bits = exp.split(" \\* ");

            for(int i = 0 ; i < bits.length ; i++){
                multi *= Integer.parseInt(bits[i]);
            }
            return multi;
        }

        throw new RuntimeException("올바른 게산식 쓰셈");
    }

    private static int findSplintPointIndexBy(String exp, char findChar){
        int bracketCount = 0;

        for(int i =0; i<exp.length();i++)
        {
            char c = exp.charAt(i);

            if(c == '('){
                bracketCount++;
            }
            else if(c == ')'){
                bracketCount--;
            }
            else if(c == findChar){
                if(bracketCount == 0){
                    return i;
                }
            }
        }
        return -1;
    }

    private static int findSplintPointIndex(String exp) {
        int bracketCount = 0;

        //강사님이 한거
        int index = findSplintPointIndexBy(exp, '+');
        if(index >= 0) return index;

        return findSplintPointIndexBy(exp, '*');

        //내가한거
        /*int findPlus = findSplintPointIndexBy(exp, '+');
        int findMulti = findSplintPointIndexBy(exp, '*');

        if(findPlus != -1){
            return findPlus;
        }
        else if(findMulti != -1){
            return findMulti;
        }

        return -1;*/
    }

    private static String stripOuterBrackets(String exp){
        int outerBracketCount = 0;

        while(exp.charAt(outerBracketCount) =='(' && exp.charAt(exp.length()-1-outerBracketCount) == ')'){
            outerBracketCount++;
        }

        if(outerBracketCount == 0) return exp;

        return exp.substring(outerBracketCount,exp.length()-outerBracketCount);
    }

}
