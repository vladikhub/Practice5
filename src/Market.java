//
// Смольков Владислав OP5 КИ23-16/1б Вариант 5
//

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Класс для хранения товаров и меню
 */
public class Market {
  public static boolean inputAccepted = false;
  public static Scanner sc;
  public static ArrayList<Goods> goods;

  public Market() {
  }

  /**
   * Сортировка товаров по цене
   */
  public static void sortGoodsByCost() {
    Collections.sort(goods, new Comparator<Goods>() {
      public int compare(Goods o1, Goods o2) {
        return Integer.compare(o1.getCost(), o2.getCost());
      }
    });
    System.out.println("Товары отсортированы.");
  }

  /**
   * Получения товаров по категории
   * @param cat
   */
  public static void getGoodsByCategory(String cat) {
    Iterator var1 = goods.iterator();
    while(var1.hasNext()) {
      Goods g = (Goods)var1.next();
      if (g.getCategory().equals(cat)) {
        getAttrGood(g);
      }
    }

  }

  /**
   * Метод для изменений параметров у товара, получает товар и номер параметра
   * @param g
   * @param numAtr
   */
  public static void changeAttribute(Goods g, int numAtr) {

    int av;
    switch (numAtr) {
      case 1:
        System.out.println("Введите название категории: ");
        String cat = null;
        do {
          try {
            cat = sc.nextLine();
            if (cat.isEmpty()){
              throw new InputEmptyString("Пустая строка");
            }
            inputAccepted = true;
          } catch (InputEmptyString ex){
            System.out.println("Нужно ввести не пустую строку");
          } finally {
            if (inputAccepted)
              System.out.println("Молодец!");
            else {
              System.out.println("Попробуй еще раз( : ");
            }
          }
        } while (!inputAccepted);
        inputAccepted = false;
        g.setCategory(cat);
        break;
      case 2:
        System.out.println("Введите название товара: ");
        String name = null;
        do {
          try {
            name = sc.nextLine();
            if (name.isEmpty()){
              throw new InputEmptyString("Пустая строка");
            }
            inputAccepted = true;
          } catch (InputEmptyString ex){
            System.out.println("Нужно ввести не пустую строку: ");
          }
        } while (!inputAccepted);
        inputAccepted = false;
        g.setName(name);
        break;
      case 3:
        System.out.println("Введите стоимость товара: ");
        av = 1;
        do {
          try {
            av = sc.nextInt();
            if ( av < 0) throw new InputNegativeNumber("Отрицательное число");
            inputAccepted = true;
          } catch (InputMismatchException | InputNegativeNumber var) {
            System.out.println("Вы должны ввести положительное число: ");
            sc.nextLine();
          }
        } while(!inputAccepted);
        inputAccepted = false;
        sc.nextLine();
        g.setCost(av);
        return;
      case 4:
        double weight = 0.0;
        System.out.println("Введите вес товара (Ч,Ч): ");
        do {
          try {
            weight = sc.nextDouble();
            if (weight < 0) throw new InputNegativeNumber("Отрицательное число");

            inputAccepted = true;
          } catch (InputMismatchException | InputNegativeNumber var8) {
            System.out.println("Вы должны ввести число: ");
            sc.nextLine();
          }
        } while (!inputAccepted);
        inputAccepted = false;
        sc.nextLine();
        g.setWeight(weight);
        return;
      case 5:
        av = 0;
        System.out.println("Выберете один из пунктов:\n1. Есть в наличии\n2. Нет в наличии\n");
        do {
          try {
            av = sc.nextInt();
            if (av > 2 || av < 1) {
              throw new InputMismatchException("Введено неправильное число");
            }
            inputAccepted = true;
          } catch (InputMismatchException var10) {
            System.out.println("Введите число от 1 до 2: ");
            sc.nextLine();
          }
        } while (!inputAccepted);
        sc.nextLine();
        inputAccepted = false;
        if (av == 1) {
          g.setAvailable(true);
        } else {
          g.setAvailable(false);
        }
        break;
    }

  }

  /**
   * Метод для вывода атрибутов товара
   * @param g
   */
  public static void getAttrGood(Goods g) {
    System.out.println("Категория: " + g.getCategory());
    System.out.println("Название: " + g.getName());
    System.out.println("Стоимость: " + g.getCost());
    System.out.println("Вес: " + g.getWeight());
    System.out.println("Наличие на складе: " + g.getAvailable());
  }

  /**
   * Метод для добавления пустого товара
   */
  public static void addPlaceForGood() {
    goods.add(new Goods());
  }

  /**
   * Метод для добавления товара с параметрами
   * @param category
   * @param name
   * @param cost
   * @param weight
   * @param available
   */
  public static void addGood(String category, String name, int cost, double weight, boolean available) {
    boolean exist = false;
    Iterator var7 = goods.iterator();

    // Проверка на существование товара
    while(var7.hasNext()) {
      Goods g = (Goods)var7.next();
      if (g.getName().equals(name)) {
        exist = true;
        break;
      }
    }

    if (!exist) {
      goods.add(new Goods(category, name, cost, weight, available));
    } else {
      System.out.println("Товар с таким названием уже существует");
    }

  }

  /**
   * Метод для вывода всех товаров
   */
  public static void showAllGoods() {
    int c = 1;
    System.out.println("Общее количество товаров: " + goods.size());
    Iterator var1 = goods.iterator();

    while(var1.hasNext()) {
      Goods g = (Goods)var1.next();
      int var10001 = c++;
      System.out.println("" + var10001 + ".");
      if (!g.getName().isEmpty()) {
        getAttrGood(g);
      } else {
        System.out.println("Категория: Не определено\nНазвание: Не определено\nСтоимость: Не определено\nВес: Не определено\nНаличие: false\n");
      }
    }

  }

  /**
   * Меню
   * @param args
   */
  public static void main(String[] args) {
    boolean f = true;
    boolean running = true;

    while(running) {
      System.out.println("\n___МЕНЮ___\n1. Показать все товары\n2. Добавить пустое место под товар\n3. Добавить товар\n4. Редактировать информацию о товаре\n5. Отсортировать список товаров по цене\n6. Отфильтровать товары по категории\n7. Показать информацию о товаре\n8. Выход\n");
      System.out.println("Выберете пункт:");
      int cost;
      double weight;
      int av;
      String cat;
      String n;
      switch (sc.nextLine()) {
        case "8":
          System.out.println("Выход из приложения...");
          running = false;
          break;
        case "1":
          showAllGoods();
          break;
        case "2":
          addPlaceForGood();
          break;
        case "3":
          cost = 0;
          weight = 0.0;
          System.out.println("Введите категорию товара: ");
          String category = sc.nextLine();

          while(category.isEmpty()) {
            category = sc.nextLine();
            System.out.println("Введите категорию товара, строка не должна быть пустой: ");
          }

          System.out.println("Введите название товара: ");
          String name = sc.nextLine();

          while(name.isEmpty()) {
            name = sc.nextLine();
            System.out.println("Введите название товара, строка не должна быть пустой: ");
          }

          System.out.println("Введите стоимость товара: ");

          do {
            try {
              cost = sc.nextInt();
              if ( cost < 0) throw new InputNegativeNumber("Отрицательное число");
              inputAccepted = true;
            } catch (InputMismatchException | InputNegativeNumber var) {
              System.out.println("Вы должны ввести положительное число: ");
              sc.nextLine();
            }
          } while(!inputAccepted);
          inputAccepted = false;
          sc.nextLine();

          System.out.println("Введите вес товара (Ч,Ч): ");
          do {
            try {
              weight = sc.nextDouble();
              if (weight < 0) throw new InputNegativeNumber("Отрицательное число");

              inputAccepted = true;
            } catch (InputMismatchException | InputNegativeNumber var8) {
              System.out.println("Вы должны ввести число: ");
              sc.nextLine();
            }
          } while (!inputAccepted);
          inputAccepted = false;
          sc.nextLine();
          System.out.println("Выберете один из пунктов:\n1. Есть в наличии\n2. Нет в наличии\n");
          av = 0;

          do {
            try {
              av = sc.nextInt();
              if (av > 2 || av < 1) {
                throw new InputMismatchException("Введено неправильное число");
              }
              inputAccepted = true;
            } catch (InputMismatchException var10) {
              System.out.println("Введите число от 1 до 2: ");
              sc.nextLine();
            }
          } while (!inputAccepted);
          sc.nextLine();
          inputAccepted = false;

          boolean available;
          if (av == 1) {
            available = true;
          } else {
            available = false;
          }
          addGood(category, name, cost, weight, available);
          break;
        case "4":
          showAllGoods();
          if (goods.isEmpty()) {
            System.out.println("Товары еще не созданы");
            break;
          }
          System.out.println("Введите номер товара, который хотите редактировать");
          int num = 0;
          do {
            try {
              num = sc.nextInt();
              if (num < 0 || num > goods.size()) {
                throw new InputMismatchException("Введен неверный номер");
              }
              inputAccepted = true;
            } catch (InputMismatchException var26) {
              System.out.println("Введите положительно число от 1 до " + goods.size() + ": ");
              sc.nextLine();
            }
          } while (!inputAccepted);
          inputAccepted = false;
          sc.nextLine();
          Goods g = (Goods) goods.get(num - 1);

          // Если выбранный товар, пустой, то заполняются все поля, если нет, то выбирается поле для редактирования
          if (g.getName().isEmpty()) {
            System.out.println("Введите категорию товара: ");
            cat = sc.nextLine();
            while(cat.isEmpty()){
              System.out.println("Введите категорию товара, строка не должна быть пустой: ");
              cat = sc.nextLine();
            }
            System.out.println("Введите название товара: ");
            n = sc.nextLine();

            while(n.isEmpty()) {
              n = sc.nextLine();
              System.out.println("Введите название товара, строка не должна быть пустой: ");
            }

            cost = 0;
            System.out.println("Введите стоимость товара: ");
            do {
              try {
                cost = sc.nextInt();
                if ( cost < 0) throw new InputNegativeNumber("Отрицательное число");
                inputAccepted = true;
              } catch (InputMismatchException | InputNegativeNumber var) {
                System.out.println("Вы должны ввести положительное число: ");
                sc.nextLine();
              }
            } while(!inputAccepted);
            inputAccepted = false;
            sc.nextLine();
            System.out.println("Введите вес товара (Ч,Ч): ");
            weight = 0.0;
            do {
              try {
                weight = sc.nextDouble();
                if (weight < 0) throw new InputMismatchException("Отрицательное число: ");

                inputAccepted = true;
              } catch (InputMismatchException var8) {
                System.out.println("Вы должны ввести число!: ");
                sc.nextLine();
              }
            } while (!inputAccepted);
            inputAccepted = false;

            System.out.println("Выберете один из пунктов:\n1. Есть в наличии\n2. Нет в наличии\n");
            av = 0;

            do {
              try {
                av = sc.nextInt();
                if (av > 2 || av < 1) {
                  throw new InputMismatchException("Введено неправильное число");
                }
                inputAccepted = true;
              } catch (InputMismatchException var10) {
                System.out.println("Введите число от 1 до 2: ");
                sc.nextLine();
              }
            } while (!inputAccepted);
            sc.nextLine();
            inputAccepted = false;


            if (av == 1) {
              available = true;
            } else {
              available = false;
            }
            System.out.println("Название товара = " + n + " !");
            g.setName(n);
            g.setCost(cost);
            g.setCategory(cat);
            g.setWeight(weight);
            g.setAvailable(available);
            break;
          }

          System.out.println("1. Категория\n2. Название\n3. Стоимость\n4. Вес\n5. Наличие\n");
          System.out.println("Введите номер атрибута, который будете менять: ");

          for(cat = sc.nextLine(); !cat.matches("[1-5]"); cat = sc.nextLine()) {
            System.out.println("Введите правильный номер атрибута: ");
          }

          changeAttribute(g, Integer.parseInt(cat));
          break;
        case "5":
          sortGoodsByCost();
          break;
        case "6":
          System.out.println("Введите категорию товара: ");
          cat = sc.nextLine();
          getGoodsByCategory(cat);
          break;
        case "7":
          System.out.println("Введите название товара: ");
          n = sc.nextLine();
          Goods needgood = new Goods();
          boolean exist = false;
          Iterator var19 = goods.iterator();

          while(var19.hasNext()) {
            Goods good = (Goods)var19.next();
            if (good.getName().equals(n)) {
              exist = true;
              needgood = good;
              break;
            }
          }
          if (exist) {
            getAttrGood(needgood);
          } else {
            System.out.println("Товара с таким именем не существует");
          }
          break;
        default:
          System.out.println("Вы выбрали несуществующйи пункт. Повторите попытку");
      }
    }

  }

  static {
    sc = new Scanner(System.in);
    goods = new ArrayList();
  }
}
