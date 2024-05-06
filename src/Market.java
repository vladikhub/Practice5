//
// Смольков Владислав OP5 КИ23-16/1б Вариант 5
//

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Класс для хранения товаров и меню
 */
public class Market {
  public static boolean inputAccepted = false;
  public static Scanner sc;
  public static ArrayList<Goods> goods;

  public Market() {
  }


  public static void groupByCat(ArrayList<Goods> list) {
    Stream<Goods> goodsStream = list.stream();
    Map<String, List<Goods>> goodsMap = goodsStream.collect(Collectors.groupingBy(Goods:: getCategory));
    for(Map.Entry<String, List<Goods>> item : goodsMap.entrySet()){
      IntStream intStream = item.getValue().stream().flatMapToInt(v -> IntStream.of(v.getCost()));
      IntSummaryStatistics intSummaryStatistics = intStream.summaryStatistics();

      System.out.println(item.getKey() + ":");
      System.out.println("(Количество продукции: " + intSummaryStatistics.getCount()
          + "\n Сумма продукций: " + intSummaryStatistics.getSum() + ")");
      for(Goods good : item.getValue()){
        System.out.println("\t" + good.getName());
      }
      System.out.println();
    }
  }


  public static void sumCosts(ArrayList<Goods> list) {
    Stream<Goods> goodsStream = list.stream();
    OptionalInt summ = goodsStream.mapToInt(Goods::getCost).reduce(Integer::sum);
    if (summ.isPresent()){
      System.out.println("Сумма стоимостей всех продуктов: " + summ.getAsInt());
    }
    goodsStream.close();
  }


  public static void filterGoods(ArrayList<Goods> list, Scanner sc) {
    Stream<Goods> goodsStream = list.stream();
    int cost = 0;
    try {
      System.out.println("Введите минимальную стоимость: ");
      cost = sc.nextInt();
    } catch (InputMismatchException ex){
      System.out.println("Неправильный ввод. Введите стоимость:");
      sc.nextLine();
      cost = sc.nextInt();
    }
    final int finalCost = cost;
    goodsStream.filter(g -> g.getCost() > finalCost).forEach(System.out::println);
    goodsStream.close();
  }


  /**
   * Меню
   * @param args
   */
  public static void main(String[] args) {
    boolean f = true;
    boolean running = true;

    ArrayList<Goods> list = new ArrayList<>();
    Goods good1 = new Goods("Молочное изделие", "Молоко", 130, 0.95, true);
    Goods good2 = new Goods("Молочное изделие", "Творог", 110, 0.5, true);
    Goods good3 = new Goods("Хлебобулочное изделиие", "Батон", 50, 0.3, true);
    Goods good4 = new Goods("Хлебобулочное изделиие", "Хлеб", 30, 0.3, true);
    Goods good5 = new Goods("Хлебобулочное изделиие", "Слойка", 65, 0.2, true);
    Goods good6 = new Goods("Мясное", "Колбаса", 250, 0.5, true);
    Goods good7 = new Goods("Мясное", "Фарш", 450, 1.0, true);
    Collections.addAll(list, good1, good2, good3, good4, good5, good6, good7);

    while(running) {
      System.out.println("""
      ___МЕНЮ___
      1. Создать поток из массива и вывести на экран
      2. Фильтровать продукты по цене
      3. Изъять дубликаты
      4. Суммировать стоимости всех товаров
      5. Сгрупировать по категории
      6. Загрузить данные в файловую систему
      7. Загрузить данные из файловой системы
      8. Выход""");
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
          Stream<Goods> goodsStream = list.stream();
          goodsStream.forEach(System.out::println);
          goodsStream.close();
          break;
        case "2":
          filterGoods(list, sc);
          break;
        case "3":
          list = (ArrayList<Goods>) list.stream().distinct().collect(Collectors.toList());
          System.out.println("Дубликаты были удалены");
          break;
        case "4":
          sumCosts(list);
          break;
        case "5":
          groupByCat(list);
          break;
        case "6":
          System.out.println("Введите категорию товара: ");
          cat = sc.nextLine();
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
            break;
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
