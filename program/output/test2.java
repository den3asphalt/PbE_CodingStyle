import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

class Animal {
    String name;

    Animal(String name) {
        this.name = name;
    }

    void speak() {
        System.out.println(name + " makes a sound.");
    }
}

class Dog extends Animal {
    Dog(String name) {
        super(name);
    }

    @Override
    void speak() {
        System.out.println(name + " barks.");
    }
}

interface Swimmer {
    void swim();
}

class Fish extends Animal implements Swimmer {
    Fish(String name) {
        super(name);
    }

    @Override
    void speak() {
        System.out.println(name + " bubbles.");
    }

    @Override
    public void swim() {
        System.out.println(name + " swims.");
    }
}

public class test2 {
    public static void main(String[] args) {
        // 変数の宣言と初期化
        List<Animal> animals = new ArrayList<>();
        animals.add(new Dog("Buddy"));
        animals.add(new Fish("Nemo"));

        // 複数のメソッドを使用
        for (Animal animal : animals) {
            animal.speak(); // ポリモーフィズムを利用
            if (animal instanceof Swimmer) {
                ((Swimmer) animal).swim();
            }
        }

        // 条件分岐のパターン
        int age = 18;
        if (age < 18) {
            System.out.println("Underage");
        } else if (age == 18) {
            System.out.println("Just turned 18");
        } else {
            System.out.println("Adult");
        }

        // ループとラムダ式
        List<String> names = List.of("Alice", "Bob", "Charlie");
        names.forEach(name -> System.out.println("Hello, " + name));

        // ジェネリクスを使ったリスト操作
        List<Integer> numbers = new ArrayList<>();
        numbers.add(5);
        numbers.add(10);
        numbers.add(15);
        numbers.forEach(n -> System.out.println("Number: " + n));

        // 例外処理
        try {
            int result = divide(10, 0); // ゼロ除算エラー
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // オーバーロードされたメソッド
        System.out.println(sum(10, 20)); // int型の加算
        System.out.println(sum(10.5, 20.5)); // double型の加算

        // クラスのインスタンス化とメソッド呼び出し
        Dog dog = new Dog("Rex");
        dog.speak();

        // 配列の操作
        int[] array = { 1, 2, 3, 4, 5 };
        for (int i : array) {
            System.out.println(i);
        }

        // ラムダ式を用いた関数型インターフェース
        Function<String, Integer> stringLength = (str) -> str.length();
        System.out.println("Length of 'Hello': " + stringLength.apply("Hello"));
    }

    // ゼロ除算を試みるメソッド
    public static int divide(int a, int b) throws ArithmeticException {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }

    // オーバーロードされた加算メソッド
    public static int sum(int a, int b) {
        return a + b;
    }

    public static double sum(double a, double b) {
        return a + b;
    }
}
