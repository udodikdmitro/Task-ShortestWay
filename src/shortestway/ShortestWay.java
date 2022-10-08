package shortestway;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class Node {
    // (x, y) представляет собой координаты ячейки матрицы, а
    // `dist` представляет их минимальное расстояние от источника
    int x, y, dist;

    Node(int x, int y, int dist) {
        this.x = x;
        this.y = y;
        this.dist = dist;
    }
}

class Main {
    // Ниже массивы детализируют все четыре возможных перемещения из ячейки
    private static final int[] row = {-1, 0, 0, 1};
    private static final int[] col = {0, -1, 1, 0};

    // Функция проверки возможности перехода на позицию (строка, столбец)
    // от текущей позиции. Функция возвращает false, если (строка, столбец)
    // недопустимая позиция или имеет значение 0 или уже посещено.
    private static boolean isValid(int[][] mat, boolean[][] visited, int row, int col) {
        return (row >= 0) && (row < mat.length) && (col >= 0) && (col < mat[0].length)
                && mat[row][col] == 1 && !visited[row][col];
    }

    // Находим кратчайший маршрут в матрице `mat` из источника
    // ячейка (i, j) в ячейку назначения (x, y)
    private static int findShortestPathLength(int[][] mat, int i, int j, int x, int y) {
        // базовый случай: неверный ввод
        if (mat == null || mat.length == 0 || mat[i][j] == 0 || mat[x][y] == 0) {
            return -1;
        }

        // Матрица `M × N`
        int M = mat.length;
        int N = mat[0].length;

        // создаем матрицу для отслеживания посещенных ячеек
        boolean[][] visited = new boolean[M][N];

        // создаем пустую queue
        Queue<Node> q = new ArrayDeque<>();
        ArrayList<Node> list = new ArrayList<>();

        // помечаем исходную ячейку как посещенную и ставим исходный узел в queue
        visited[i][j] = true;
        q.add(new Node(i, j, 0));
        // сохраняет длину самого длинного пути от источника к месту назначения
        int min_dist = Integer.MAX_VALUE;

        // цикл до тех пор, пока queue не станет пустой
        while (!q.isEmpty()) {
            // удалить передний узел из очереди и обработать его
            Node node = q.poll();

            // (i, j) представляет текущую ячейку, а `dist` хранит ее
            // минимальное расстояние от источника
            i = node.x;
            j = node.y;
            int dist = node.dist;

            list.add(node);
            System.out.println("Way: " + i + "," + j);

            // если пункт назначения найден, обновляем `min_dist` и останавливаемся
            if (i == x && j == y) {
                min_dist = dist;
                break;
            }

            // проверяем все четыре возможных перемещения из текущей ячейки
            // и ставим в queue каждое допустимое движение
            for (int k = 0; k < 4; k++) {
                // проверяем, можно ли выйти на позицию
                // (i + row[k], j + col[k]) от текущей позиции
                if (isValid(mat, visited, i + row[k], j + col[k])) {
                    // отметить следующую ячейку как посещенную и поставить ее в queue
                    visited[i + row[k]][j + col[k]] = true;
                    q.add(new Node(i + row[k], j + col[k], dist + 1));
                }
            }
        }

        for (int f = 0; f < list.size(); f++) {
            System.out.println(list.get(f));
        }

        if (min_dist != Integer.MAX_VALUE) {
            return min_dist;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[][] mat =
                {
                        {1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
                        {0, 1, 1, 1, 1, 1, 0, 1, 0, 1},
                        {0, 0, 1, 0, 1, 1, 1, 0, 0, 1},
                        {1, 0, 1, 1, 1, 0, 1, 1, 0, 1},
                        {0, 0, 0, 1, 0, 0, 0, 1, 0, 1},
                        {1, 0, 1, 1, 1, 0, 0, 1, 1, 0},
                        {0, 0, 0, 0, 1, 0, 0, 1, 0, 1},
                        {0, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                        {1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
                        {0, 0, 1, 0, 0, 1, 1, 0, 0, 1},
                };

        int min_dist = findShortestPathLength(mat, 0, 0, 7, 5);

        if (min_dist != -1) {
            System.out.println("The shortest path from source to destination " +
                    "has length " + min_dist);
        } else {
            System.out.println("Destination cannot be reached from source");
        }
    }
}