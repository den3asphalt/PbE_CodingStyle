import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test3 {

    public static void main(String[] args) {
        // ネストされたループ
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println("Nested loop: i = " + i + ", j = " + j);
            }
        }

        // 条件分岐とループのネスト
        int[] numbers = { 1, 2, 3, 4, 5 };
        for (int number : numbers) {
            if (number % 2 == 0) {
                System.out.println("Even number: " + number);
            } else {
                System.out.println("Odd number: " + number);
                // 再帰的な呼び出し
                recursiveCall(number);
            }
        }

        // マップの操作とネストされた条件分岐
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("A", Arrays.asList(1, 2, 3));
        map.put("B", Arrays.asList(4, 5, 6));

        for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
            System.out.println("Processing list for key: " + entry.getKey());
            for (Integer value : entry.getValue()) {
                if (value % 2 == 0) {
                    System.out.println("Even value: " + value);
                } else {
                    System.out.println("Odd value: " + value);
                }
            }
        }

        // 匿名クラスの使用
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("This is an anonymous class example.");
            }
        };
        runnable.run();

        // ストリームとラムダ式のネスト
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        names.stream()
                .filter(name -> name.length() > 3)
                .map(name -> name.toUpperCase())
                .forEach(System.out::println);

        // ネストされた条件とループ
        int[][] matrix = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 9 }
        };

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] % 2 == 0) {
                    System.out.println("Even value at matrix[" + i + "][" + j + "]: " + matrix[i][j]);
                } else {
                    System.out.println("Odd value at matrix[" + i + "][" + j + "]: " + matrix[i][j]);
                }
            }
        }

        // ネストされたラムダ式を使ったマップ操作
        List<Map<String, Integer>> listOfMaps = Arrays.asList(
                Map.of("A", 1, "B", 2),
                Map.of("C", 3, "D", 4));

        listOfMaps.stream()
                .flatMap(map_ -> map_.entrySet().stream())
                .filter(entry -> entry.getValue() % 2 == 0)
                .forEach(entry -> System.out.println(entry.getKey() + " : " + entry.getValue()));

        // ネストされたメソッド呼び出し
        int x = 10;
        int y = 20;
        int result = addAndMultiply(x, y);
        System.out.println("Result of add and multiply: " + result);
    }

    // 再帰的なメソッド
    public static void recursiveCall(int number) {
        if (number > 0) {
            System.out.println("Recursive call with number: " + number);
            recursiveCall(number - 1);
        }
    }

    // ネストされた計算を行うメソッド
    public static int addAndMultiply(int a, int b) {
        int sum = add(a, b);
        return multiply(sum, 2);
    }

    // 足し算を行うメソッド
    public static int add(int a, int b) {
        return a + b;
    }

    // 掛け算を行うメソッド
    public static int multiply(int a, int b) {
        return a * b;
    }
}
