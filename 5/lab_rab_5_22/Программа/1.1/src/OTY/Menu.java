package OTY;

//класс меню
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Menu {
    public ArrayList<MenuItem> items;  //список полей меню
    private boolean exit;   //логическая переменная для выхода из меню
    private boolean back;   //для возврата назад на один уровень
    private boolean backToMainMenu;  //для возврата в главное меню из вложенного
    public Menu subMenu;  //вложенное меню

    public Menu() {  //конструктор
        items = new ArrayList();
        exit = false;
        back = false;
        backToMainMenu = false;
    }
    public void printMenu() {  //вывод на экран всех полей меню
        for(int i=0;i<items.size();i++) {
            System.out.println(i+1 + "." + items.get(i).getTitle());
        }
    }
    private void chooseItem() {  //выбор пункта меню
        int choice = 0;
        do {
            try {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    String input = reader.readLine();
                    choice = Integer.parseInt(input);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода: введена строка или символ." + e.getMessage());
            }
            if (choice <= 0 || choice > items.size())
                System.out.println("Ошибка ввода: выбранный пункт должен лежать в пределах от 1 до " + items.size() + ".");
        } while(choice <= 0 || choice > items.size());
        items.get(choice-1).run();             //вызов метода run() соответсвующего пункта
    }
    public void run() {  //запуск меню
        items.add(new MenuItem("Выход") { //добавление поля "Выход" в конец меню
            @Override
            public void run() {
                System.out.println("Завершение программы...");
                exit = true;
            }
        });

        while(!exit) {  //цикл пока не выбран пункт "Выход"
            System.out.println("\nГлавное меню:");
            printMenu();
            chooseItem();
            if(subMenu != null && subMenu.exit == true)
                this.exit = true;
        }
    }
    public void runAsSubMenu() {  //запуск вложенного меню
        items.add((new MenuItem("В главное меню") {  //добавление поля "В главное меню"
            @Override
            public void run() {
                System.out.println("Иду в главное меню...");
                backToMainMenu = true;
            }
        }));
        items.add((new MenuItem("Назад") {  //добавление поля "Назад"
            @Override
            public void run() {
                System.out.println("Возвращаюсь назад...");
                back = true;
            }
        }));
        items.add((new MenuItem("Выход") {  //добавление поля "Назад"
            @Override
            public void run() {
                System.out.println("Завершение программы...");
                exit = true;
            }
        }));

        while(!back && !exit && !backToMainMenu) {  //цикл пока не выбран пункт "Назад","Выход" или "В главное меню"
            System.out.println("\nВложенное меню:");
            printMenu();
            chooseItem();
            if(subMenu != null) {
                if(subMenu.backToMainMenu == true)
                    this.backToMainMenu = true;
                else if(subMenu.exit == true)
                    this.exit = true;
            }

        }
    }
    public void closeSubMenu() {  //закрыть вложенное меню
        backToMainMenu = true;
    }
}

