public class test4 {

    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }

    public void intTest() {
        int number = 10;

        if (number > 0) {
            System.out.println("The number is positive.");
        } else if (number < 0) {
            System.out.println("The number is negative.");
        } else {
            System.out.println("The number is zero.");
        }

        if (number % 2 == 0) {
            System.out.println("The number is even.");
        } else {
            System.out.println("The number is odd.");
        }
    }

    public String comment() {
        // This is a comment
        System.out.println("This is a comment");
        // Comment
        System.out.println();

        for (int i = 0; i < 10; i++) {
            System.out.println();
        }

        int number = 10;

        // This is a comment
        System.out.println("This is a comment");

        // This is a comment
        System.out.println("This is a comment");

        if (number > 0) {
            // This is a comment
            return "This is a comment";
        }

        System.out.println("Hello, World!");

        try {
            int a = 0;
            int b = 0;
            int c = a / b;
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }

        return "";
    }

    private void privateMethod() {
        System.out.println();
    }
    private void privateMethod2() {
        System.out.println();
    }

    final void finalMethod() {
        System.out.println();
    }
    final void finalMethod2() {
        System.out.println();
    }

    static class privateTest {
        private void test2() {
            System.out.println();
        }
    }

    static class finalTest {
        final void test() {
            System.out.println();
        }
    }
    static class privateTest2 {

        private void test2() {
            System.out.println();
        }
    }

    static class finalTest3 {

        final void test() {
            System.out.println();
        }
    }

    public class publicTest {

        public int a = 10;

        public void test() {
            System.out.println();
        }

        public int test2() {
            return 0;
        }
    }
}
