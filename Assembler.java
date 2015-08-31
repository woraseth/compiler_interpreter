
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Assembler {

  public static void main(String[] args) throws Exception {  // args[0] is an assembly file
    BufferedReader br = new BufferedReader(new FileReader(args[0]));
    int pc = 0;
    Map<String, Integer> map = new HashMap();
    for (String line = br.readLine(); line != null; line = br.readLine()) {
      line = line.toUpperCase();
      switch (line.charAt(0)) {
        case ' ':             // instruction
          //System.out.println("line.split('') = " + java.util.Arrays.toString(line.split(" ")));
          switch (line.split(" ")[1]) {
            case "PIM":
            case "JIZ":
            case "CLL":
              pc += 2;
              break;
            default:
              pc++;
          }
          break;
        case '#':             // comment
          break;
        default:
          map.put(line, pc);  // label
      }
    }
    //System.out.println(map);
    br.close();

    br = new BufferedReader(new FileReader(args[0]));
    for (String line = br.readLine(); line != null; line = br.readLine()) {
      line = line.toUpperCase();
      if (line.charAt(0) == ' ') {
        switch (line.split(" ")[1]) {
          case "PIM":
            System.out.printf("%X%n", Prism.PIM);
            break;
          case "JIZ":
            System.out.printf("%X%n", Prism.JIZ);
            break;
          case "CLL":
            System.out.printf("%X%n", Prism.CLL);
            break;
          case "POP":
            System.out.printf("%X%n", Prism.POP);
            break;
          case "STO":
            System.out.printf("%X%n", Prism.STO);
            break;
          case "LDP":
            System.out.printf("%X%n", Prism.LDP);
            break;
          case "ADD":
            System.out.printf("%X%n", Prism.ADD);
            break;
          case "SUB":
            System.out.printf("%X%n", Prism.SUB);
            break;
          case "MUL":
            System.out.printf("%X%n", Prism.MUL);
            break;
          case "DIV":
            System.out.printf("%X%n", Prism.DIV);
            break;
          case "AND":
            System.out.printf("%X%n", Prism.AND);
            break;
          case "OR":
            System.out.printf("%X%n", Prism.OR);
            break;
          case "NOT":
            System.out.printf("%X%n", Prism.NOT);
            break;
          case "RET":
            System.out.printf("%X%n", Prism.RET);
            break;
          case "PRN":
            System.out.printf("%X%n", Prism.PRN);
            break;
          case "HLT":
            System.out.printf("%X%n", Prism.HLT);
            break;
          default:
            System.out.println("Error.  Unknown command " + line);
            return;
        }
        switch (line.split(" ")[1]) {
          case "JIZ":
          case "CLL":
            System.out.printf("%X%n", map.get(line.split(" ")[2]));
            break;
          case "PIM":
            System.out.println(line.split(" ")[2]);
            break;
        }
      }
    }
    //System.out.println(map);
    br.close();
  }
}
