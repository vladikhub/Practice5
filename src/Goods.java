//
// Смольков Владислав OP5 КИ23-16/1б Вариант 5
//

import java.util.Scanner;

/**
 * Класс для Товара
 */
public class Goods {
  private String category = "";
  private String name = "";
  private int cost = 0;
  private double weight = 0.0;
  private boolean available = false;
  private Scanner sc;

  /**
   * Установщик наличия
   * @param available
   */
  public void setAvailable(boolean available) {
    this.available = available;
  }

  /**
   * Получение наличия
   * @return available
   */
  public boolean getAvailable() {
    return this.available;
  }

  /**
   * Установщик цены
   * @param cost
   */
  public void setCost(int cost) {
    try {
      if (cost < 0) {
        throw new InputNegativeNumber("Введено отрицательное число");
      }

      this.cost = cost;
    } catch (InputNegativeNumber var3) {
      System.out.println("Введите положительное число: ");
      this.cost = this.sc.nextInt();
    }

  }

  /**
   * Установщик категории
   * @param category
   */
  public void setCategory(String category) {
    try {
      if (category.isEmpty()) {
        throw new InputEmptyString("Введена пустая строка");
      }

      this.category = category;
    } catch (InputEmptyString var3) {
      System.out.println("Строка должна содержать символы: ");
      this.category = this.sc.nextLine();
    }

  }

  /**
   * Установщик имени
   * @param name
   */
  public void setName(String name) {
    try {
      if (name.isEmpty()) {
        throw new InputEmptyString("Введена пустая строка");
      }

      this.name = name;
    } catch (InputEmptyString var3) {
      System.out.println("Строка должна содержать символы: ");
      this.name = this.sc.nextLine();
    }

  }

  /**
   * Установщик веса
   * @param weight
   */
  public void setWeight(double weight) {
    try {
      if (weight < 0.0) {
        throw new InputNegativeNumber("Введено отрицательное число");
      }

      this.weight = weight;
    } catch (InputNegativeNumber var4) {
      System.out.println("Введите положительное число: ");
      this.weight = this.sc.nextDouble();
    }

  }

  /**
   * Экземпляр пустого товара
   */
  public Goods() {
    this.sc = new Scanner(System.in);
  }

  /**
   * Экземпляр товара с параметрами
   * @param category
   * @param name
   * @param cost
   * @param weight
   * @param available
   */
  public Goods(String category, String name, int cost, double weight, boolean available) {
    this.sc = new Scanner(System.in);
    this.category = category;
    this.name = name;
    this.cost = cost;
    this.weight = weight;
    this.available = available;
  }

  /**
   * Получение категории
   * @return category
   */
  public String getCategory() {
    return this.category;
  }

  /**
   * Получение имени
   * @return name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Получение цены
   * @return cost
   */
  public int getCost() {
    return this.cost;
  }

  /**
   * Получение веса
   * @return weight
   */
  public double getWeight() {
    return this.weight;
  }
}
