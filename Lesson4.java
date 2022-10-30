package ru.geekbrains;

import java.util.Random;
import java.util.Scanner;

public class Lesson4 {

    static final char DOT_HUMAN = 'X'; // Фишка игрока - чедовек
    static final char DOT_AI = 'O'; // Фишка игрока - компьютер
    static final char DOT_EMPTY = '•'; // Признак пустого поля
    static final Scanner sc = new Scanner(System.in); // Вспомогательный класс для ввода данных
    static final Random rand = new Random(); // Вспомогательный класс генерации случайных чисел
    static char[][] field; // Двумерный массив, хранит текущее состояние игрового поля
    static int fieldSixeX; // Размерность игрового поля
    static int fieldSizeY; // Размерность игорового поля


    /**
     * Инициализация объектов игры
     */

    private static void initialize() {
        //Установим размерность игрового поля
        fieldSixeX = 3;
        fieldSizeY = 3;
        field = new char[fieldSixeX][fieldSizeY];
        for (int x = 0; x < fieldSixeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                //Проинициализируем все элементы массива DOT_EMPTY
                field[x][y] = DOT_EMPTY;
            }
        }
    }

    /**
     * Отрисовка игрового поля
     */

    static void printField() {
        System.out.print("+");
        for (int i = 0; i < fieldSixeX * 2 + 1; i++) {
            System.out.print(i % 2 == 0 ? "-" : i / 2 + 1);
        }
        System.out.println();

        for (int x = 0; x < fieldSixeX; x++) {
            System.out.print(x + 1 + "|");

            for (int y = 0; y < fieldSizeY; y++) {
                System.out.print(field[x][y] + "|");
            }
            System.out.println();
        }
        for (int i = 0; i <= fieldSixeX * 2 + 1; i++) {
            System.out.print("-");

        }
        System.out.println();
    }

    /**
     * Обработка хода игрока (человек)
     */

    static void humanTurn() {
        int x, y;
        do {
            System.out.print("Введите координаты хода X и Y\n(от 1 до 3) через пробел >>>>");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!(isCellValid(x, y) && isCellEmpty(x, y)));
        field[x][y] = DOT_HUMAN;
    }

    /**
     * Обработка хода игрока (компьютер)
     */

    static void aiTurn () {
        int x, y;
        do {
            x = rand.nextInt(fieldSixeX);
            y = rand.nextInt(fieldSizeY);
        } while (!isCellEmpty(x, y));
        field[x][y] = DOT_AI;

    }

    /**
     * Проверка, ячейка является пустой (DOT_EMPTY)
     * @param x
     * @param y
     * @return
     */

    static boolean isCellEmpty ( int x, int y){
        return field[x][y] == DOT_EMPTY;
    }

    /**
     * Проверка корректности ввода (координаты хода не должны превышать размернность
     * массива, описывающее игровое поле
     * @param x
     * @param y
     * @return
     */
    static boolean isCellValid ( int x, int y){
        return x >= 0 && x < fieldSixeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * проверка победы игрока (человек/компьютер)
     * @param c
     * @return
     */

    static boolean checkWin(char c) {
        // Проверка по трем горизонталям
        /*
        if (field[0][0] == c && field[0][1] == c && field[0][2] == c) return true;
        if (field[1][0] == c && field[1][1] == c && field[1][2] == c) return true;
        if (field[2][0] == c && field[2][1] == c && field[2][2] == c) return true;

        // Проверка по трем вертикалям
        if (field[0][0] == c && field[1][0] == c && field[2][0] == c) return true;
        if (field[0][1] == c && field[1][1] == c && field[2][1] == c) return true;
        if (field[0][2] == c && field[1][2] == c && field[2][2] == c) return true;

        // Проверка по двум диагоналям
        if (field[0][0] == c && field[1][1] == c && field[2][2] == c) return true;
        if (field[0][2] == c && field[1][1] == c && field[2][0] == c) return true;

        return false;
         */

        // метод проверки игрока (человек/компьтер)
        for (int i = 0; i < 3; i++)
            if ((field[i][0] == c && field[i][1] == c && field[i][2] == c) ||
                    (field[0][i] == c && field[1][i] == c && field[2][i] == c)) return true;

        if ((field[0][0] == c && field[1][1] == c && field[2][2] == c) ||
                (field[2][0] == c && field[1][1] == c && field[0][2] == c)) return true;
        return false;
    }



    /**
     * Проверка игры на ничью
     * @return
     */

    static boolean checkDraw () {

        for (int x = 0; x < fieldSixeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if(isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    /**
     * Метод проверки состояния игры
     * @param dot - игровая фишка
     * @param s - победный слоган
     * @return
     */

    static boolean gameChecks (char dot, String s) {
        if(checkWin(dot)) {
            System.out.println(s);
            return true; // Завершение игры
        }
        if (checkDraw()) {
            System.out.println("Ничья!");
            return true; // Завершение игры
        }
        // Продолжаем игру
        return false;
    }



    public static void main (String[]args){

        while (true) {
            initialize();
            printField();

            while (true) {
                humanTurn(); // Обработка хода игрока (человек)
                printField();
                if (gameChecks(DOT_HUMAN, "Вы победили!"))
                    break;
                aiTurn(); // Обработка хода игрока (компьютер)
                printField();
                if (gameChecks(DOT_AI, "Победил компьютер!"))
                    break;
            }
            System.out.println("Вы желаете еще раз сыграть , Y - да");
            if (!sc.next().equalsIgnoreCase("Y"))
                break;
        }

    }
}
