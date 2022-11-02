import java.util.TreeMap;
import java.util.Scanner;


public class Main {

    public static void main(String[] arguments)  {
        Converter converter = new Converter();
        Scanner scn = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String exp = scn.nextLine();
        String newExp = exp.replaceAll(" ", ""); // убираем пробелы

        char[] chars = new char[10]; //создаем пустой массив длиной 10 символов(!)

        //Определяем арифметическое действие:
        String[] actions = {"+", "-", "/", "*"};
        String[] regexActions = {"\\+", "-", "/", "\\*"};
        int actionIndex = -1;
        int count =0;

        //проверяем, чтобы выражение было адеватной длины
        if(newExp.length() > 10) {
            System.out.println("слишком длинное выражение");
            // return;
        }

        for (int i = 0; i < newExp.length(); i++) {  // проверяем кол-во операторов

            chars[i] = newExp.charAt(i);
            if (chars[i] == '+' || chars[i] == '-' || chars[i] == '*' || chars[i] == '/') {
                count += 1;
            }
            if (count >= 2) {
                System.out.println("Слишком много операторов, выбери только одну операцию");
                // return;
            }
        }
        for (int i = 0; i < actions.length; i++) {
            if(newExp.contains(actions[i])){
                actionIndex = i;
                break; // didn't find any valid actions in created array of actions = break the process
            }
        }

        if(actionIndex == -1){
            System.out.println("Некорректное выражение, можно использовать только + - / *");
            //return;
        }

        String[] data = newExp.split(regexActions[actionIndex]); //разбиваем строку по символу-разделителю

        if(converter.isRoman(data[0]) == converter.isRoman(data[1])){   // check whether numbers are in same format
            int a = 11, b=11;
            boolean isRoman = converter.isRoman(data[0]);


            // проверяем, если оба значения в римском формате:
            if(isRoman && data[0].matches("(?i)I|II|III|IV|V|VI|VII|VIII|IX|X") &&
                    data[1].matches("(?i)I|II|III|IV|V|VI|VII|VIII|IX|X")){

                b = converter.romanToInt(data[1]);
                a = converter.romanToInt(data[0]);

            } //проверяем, если оба значения в арабском формате от 1 до 10
            else if (data[0].matches("(?i)1|2|3|4|5|6|7|8|9|10")  && data[1].matches("(?i)1|2|3|4|5|6|7|8|9|10")) {
                a = Integer.parseInt(data[0]);
                b = Integer.parseInt(data[1]);
            } else //if (Integer.parseInt(data[0])>10 || Integer.parseInt(data[1])>10)// Иначе выводим ошибку о том, что значения не соответсвуют нужному формату
            {

                System.out.println("Значения должны быть в диапазоне 1..10 и I..X; ");
                //return;
            }


            int result = switch (actions[actionIndex]) {
                case "+" -> a + b;
                case "-" -> a - b;
                case "*" -> a * b;
                default -> a / b;
            };

            if(isRoman){
                if (result>0) {
                    System.out.println(converter.intToRoman(result));} else {
                    System.out.println("результат не может быть <1 и >10 при использовании римских чисел"); // проверкa > 0
                }

            } else {
                System.out.println("Ответ: "+result);
                //если числа были арабские, возвращаем результат в арабском числе
                //System.out.println(result);
            }

        }else{
            System.out.println("Числа должны быть ОДИНАКОГО! формате");
        }


    }

}


class Converter {
    TreeMap<Character, Integer> romanKeyMap = new TreeMap<>();
    TreeMap<Integer, String> arabianKeyMap = new TreeMap<>();

    Converter() {
        romanKeyMap.put('I', 1);
        romanKeyMap.put('V', 5);
        romanKeyMap.put('X', 10);


        arabianKeyMap.put(100, "C");
        arabianKeyMap.put(90, "XC");
        arabianKeyMap.put(50, "L");
        arabianKeyMap.put(40, "XL");
        arabianKeyMap.put(10, "X");
        arabianKeyMap.put(9, "IX");
        arabianKeyMap.put(5, "V");
        arabianKeyMap.put(4, "IV");
        arabianKeyMap.put(1, "I");
        arabianKeyMap.put(0, "0");


    }

    boolean isRoman(String number){

        if (romanKeyMap.containsKey(number.charAt(0)))  {
            return romanKeyMap.containsKey((number.charAt(0)));
        } else {return false;}
    }

    String intToRoman(int number) {
        String roman = "";
        int arabianKey;
        if (number >0) {do {
            arabianKey = arabianKeyMap.floorKey(number);
            roman += arabianKeyMap.get(arabianKey);
            number -= arabianKey;
        } while (number != 0 ); }  ;
        return roman;
    }

    int romanToInt(String num){

        int l = num.length() - 1;
        char[] arr = num.toCharArray();
        int arab;
        int result = romanKeyMap.get(arr[l]);
        for (int i = l - 1; i >= 0; i--) {
            arab = romanKeyMap.get(arr[i]);

            if (arab < romanKeyMap.get(arr[i+1])) {
                result -= arab;
            } else {result +=arab;}

        }
        return result;
    }

}
