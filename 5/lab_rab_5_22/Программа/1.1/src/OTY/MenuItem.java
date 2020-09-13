package OTY;
//абстрактный класс
//хранит наименование поля меню и абстрактный метод run()

public abstract class MenuItem {
    private String title;

    public MenuItem(String title) {
        this.title = title;
    }
    public String getTitle(){
        return title;
    }
    public abstract void run();
}

