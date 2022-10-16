package shortestway;

import java.util.*;

class Point {
    // (x, y) представляет собой координаты ячейки матрицы, а
    // `dist` представляет их минимальное расстояние от источника
    private final Pair coordinates;
    private final List<Pair> path;

    public int getX() {
        return coordinates.x;
    }

    public int getY() {
        return coordinates.y;
    }

    public List<Pair> getPath() {
        return path;
    }

    Point(int newX, int newY, List<Pair> previousPath) {
        this.coordinates = new Pair(newX, newY);
        List<Pair> newPath = new ArrayList<>(previousPath);
        newPath.add(coordinates);
        this.path = newPath;
    }

    static class Pair {

        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "[" + x +"," + y + ']';
        }
    }
}

class LabyrinthWay {
    // Ниже массивы детализируют все четыре возможных перемещения из ячейки
    private static final int[] row = {-1, 0, 0, 1};
    private static final int[] col = {0, -1, 1, 0};

    // Функция проверки возможности перехода на позицию (строка, столбец)
    // от текущей позиции. Функция возвращает false, если (строка, столбец)
    // недопустимая позиция или имеет значение 0 или уже посещено.
    private static boolean isValid(int[][] mat, boolean[][] visited, int rowNumber, int colNumber) {
        return (rowNumber >= 0) && (rowNumber < mat.length) && (colNumber >= 0) && (colNumber < mat[0].length)
                && mat[rowNumber][colNumber] == 1 && !visited[rowNumber][colNumber];
    }

    // Находим кратчайший маршрут в матрице `mat` из источника
    // ячейка (i, j) в ячейку назначения (x, y)
    public static int findShortestPathLength(int[][] mat, int startX, int startY, int destinationX, int destinationY) {
        // базовый случай: неверный ввод
        if (mat == null || mat.length == 0 || mat[startX][startY] == 0 || mat[destinationX][destinationY] == 0) {
            throw new IllegalArgumentException("Wrong coordinates");
        }

        // Матрица `M × N`
        int M = mat.length;
        int N = mat[0].length;

        // создаем матрицу для отслеживания посещенных ячеек
        boolean[][] visited = new boolean[M][N];

        // создаем пустую queue
        Queue<Point> q = new ArrayDeque<>();

        // помечаем исходную ячейку как посещенную и ставим исходный узел в queue
        visited[startX][startY] = true;
        Point startPoint = new Point(startX, startY, new ArrayList<>());
        q.add(startPoint);
        // сохраняет длину самого длинного пути от источника к месту назначения
        Integer min_dist = null;

        // цикл до тех пор, пока queue не станет пустой
        while (!q.isEmpty()) {
            // удалить передний узел из очереди и обработать его
            Point currentPoint = q.poll();

            int currentX = currentPoint.getX();
            int currentY = currentPoint.getY();

            // если пункт назначения найден, обновляем `min_dist` и останавливаемся
            if (currentX == destinationX && currentY == destinationY) {
                min_dist = currentPoint.getPath().size() - 1;
                System.out.println(currentPoint.getPath());
                break;
            }

            // проверяем все четыре возможных перемещения из текущей ячейки
            // и ставим в queue каждое допустимое движение
            for (int k = 0; k < 4; k++) {
                // проверяем, можно ли выйти на позицию
                // (startX + row[k], startY + col[k]) от текущей позиции
                if (isValid(mat, visited, currentX + row[k], currentY + col[k])) {
                    // отметить следующую ячейку как посещенную и поставить ее в queue
                    visited[currentX + row[k]][currentY + col[k]] = true;
                    q.add(new Point(currentX + row[k], currentY + col[k], currentPoint.getPath()));
                }
            }
        }


        if (min_dist != null) {
            return min_dist;
        } else {
            throw new RuntimeException("There is no way");
        }

    }
}