import model.Faculty;
import controller.Controllers;

public class Main {
  public static void main(String[] args) {
    Faculty fac = new Faculty("Doodz");

    System.out.println(fac.getName());

    try {
      Controllers.insertFaculty("Doods");

    } catch (Exception e) {
    }
  }
}
