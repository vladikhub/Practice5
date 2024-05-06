//
// Смольков Владислав OP5 КИ23-16/1б Вариант 5
//

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
import java.util.logging.Logger;
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
  private static final Logger LOGGER = Logger.getLogger(Market.class.getName());

  /**
   * Метод группировки по категории, принимает
   * @param list
   */
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
    goodsStream.close();
  }

  /**
   * Метод для сложения всех сумм товаров, принимает
   * @param list
   */
  public static void sumCosts(ArrayList<Goods> list) {
    Stream<Goods> goodsStream = list.stream();
    OptionalInt summ = goodsStream.mapToInt(Goods::getCost).reduce(Integer::sum);
    if (summ.isPresent()){
      System.out.println("Сумма стоимостей всех продуктов: " + summ.getAsInt());
    }
    goodsStream.close();
  }

  /**
   * Метод фильтрации товаров по цене, принимает
   * @param list
   * @param sc
   */
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
    String fileName = "goods.txt";
    ArrayList<Goods> list = new ArrayList<>();
    Goods good1 = new Goods("Dairy product", "Milk", 130, 0.95, true);
    Goods good2 = new Goods("Dairy product", "Cottage cheese", 110, 0.5, true);
    Goods good3 = new Goods("Bakery product", "Loaf", 50, 0.3, true);
    Goods good4 = new Goods("Bakery product", "Bread", 30, 0.3, true);
    Goods good5 = new Goods("Bakery product", "Puff", 65, 0.2, true);
    Goods good6 = new Goods("Meat", "Sausage", 250, 0.5, true);
    Goods good7 = new Goods("Meat", "Ground meat", 450, 1.0, true);
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
          try (FileOutputStream file = new FileOutputStream(fileName, false)){
            String text;
            for (Goods good : list){
              text = good.toString() + "\n";
              file.write(text.getBytes());
            }
            System.out.println("Файл успешно записан");
          } catch ( IOException ex){
            LOGGER.severe("Ошибка" + ex.getMessage());
          }
          break;
        case "7":
          try (FileInputStream in = new FileInputStream(fileName);
              BufferedInputStream file = new BufferedInputStream(in, 200)) {
            int i;
            StringBuilder sb = new StringBuilder();
            while ((i = file.read()) != -1){
              sb.append((char) i);
            }
            if (sb.isEmpty()){
              throw new InputEmptyString("Пустая строка");
            }
            String name = null;
            String cat = null;
            int cost = 0;
            double w = 0.0;
            String str_goods = sb.toString();
            ArrayList<Goods> goods = new ArrayList<>();
            for (String goodLine : str_goods.split("\n")){
              String fields = goodLine.split(":")[1];
              name = fields.split("name = ")[1].split(";")[0].strip();
              cat = fields.split("category = ")[1].split(";")[0].strip();
              cost = Integer.parseInt(fields.split("cost = ")[1].split(";")[0].strip());
              w = Double.parseDouble(fields.split("weight = ")[1].split(";")[0].replace(",", ".").strip());
              goods.add(new Goods(cat, name, cost, w, true));
            }
            list.addAll(goods);
            System.out.println("Товары добавлены");
          } catch (IOException ex){
            LOGGER.severe("Ошибка" + ex.getMessage());
          }catch (InputEmptyString ex){
            System.out.println("Нужно сначала записать файл");
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
