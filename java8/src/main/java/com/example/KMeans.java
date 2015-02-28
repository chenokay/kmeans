package com.example;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.minBy;

/**
 * Created by evacchi on 27/02/15.
 */
public class KMeans {

    int n = 10;
    int iters = 15;

    public void run(List<Point> xs) {
        Stream<Point> centroids = xs.stream().limit(n);
        for (int i = 0; i < iters; i++) {
            centroids = clusters(xs, centroids.collect(toList()))
                        .stream().map(this::average);
        }
        clusters(xs, centroids.collect(toList()));
    }

    public Collection<List<Point>> clusters(List<Point> xs, List<Point> centroids) {
        return xs.stream().collect(groupingBy((Point x) -> closest(x, centroids))).values();
    }

    public Point closest(final Point x, List<Point> choices) {
        return choices.stream()
            .collect(minBy((y1, y2) -> dist(x, y1) < dist(x, y2) ? -1 : 1)).get();
    }

    public double sq(double x) { return x*x; }

    public double dist(Point x, Point y) { return x.minus(y).getModulus(); }

    public Point average(List<Point> xs) {
        return xs.stream().reduce(Point::plus).get().div(xs.size());
    }

}

@Data @AllArgsConstructor class Point {
    double x,y;
    public Point plus(Point p2) {
        return new Point(x + p2.getX(), y + p2.getY());
    }
    public Point minus(Point p2) {
        return new Point(x - p2.getX(), y - p2.getY());
    }
    public Point div(double d) {
        return new Point(x/d, y/d);
    }
    public Double getModulus() { return Math.sqrt(sq(x) + sq(y)); }
    private double sq(double x) { return x*x; }
}