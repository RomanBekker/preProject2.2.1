package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

        //Создаем несколько пользователей (2) с машинами:
        User userMitCar1 = new User("User5", "Lastname5", "user5@mail.ru");
        Car car1 = new Car("Model1", 111);
        userMitCar1.setCar(car1);

        User userMitCar2 = new User("User6", "Lastname6", "user6@mail.ru");
        Car car2 = new Car("Model2", 222);
        userMitCar2.setCar(car2);

        //Добавляем их в базу данных:
        userService.add(userMitCar1);
        userService.add(userMitCar2);

        //Достаем юзера, владеющего машиной по ее модели и серии:
        System.out.println(userService.selectUser("Model2", 222));

        //Вытаскиваем пользователей с машинами и без машин:
        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            if (user.getCar() != null) {
                System.out.println("Model = " + user.getCar().getModel());
                System.out.println("Series = " + user.getCar().getSeries());
            }
            System.out.println();
        }
        context.close();
    }
}
