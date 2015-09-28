package zinjvi.java8;

import com.google.common.util.concurrent.Monitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by Vitaliy on 9/2/2015.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        test1();
    }

    private static class User {
        private Integer id;
        private String name;
        private String role;

        public User() {
        }

        public User(Integer id, String name, String role) {
            this.id = id;
            this.name = name;
            this.role = role;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    public static void test1() throws IOException {
        Stream<User> userStream = Arrays.stream(new User[]{
                new User(1, "Max", "a"),
                new User(2, "Bob", "u"),
                new User(3, "Dima", "u"),
                new User(4, "Sasha", "a"),
                new User(5, "Roma", "u")
        });
        userStream.filter(user -> "u".equals(user.getRole()))
                .map(User::getName)
                .sorted(String::compareTo)
                .forEach(System.out::println);
    }

    public static void test2() throws IOException {
        Files.lines(Paths.get("D:\\projects\\test\\src\\main\\java\\zinjvi\\java8\\test.txt"))
                .flatMap(line -> Arrays.stream(line.split(" ")))
                .map(s -> )
    }

}
