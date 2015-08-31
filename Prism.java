// Worasait Suwannik

import java.io.BufferedReader;
import java.io.FileReader;

public class Prism {

  public static final int PIM = 0;
  public static final int JIZ = 1;
  public static final int CLL = 2;
  public static final int POP = 3;
  public static final int STO = 4;
  public static final int LDP = 5;
  public static final int ADD = 6;
  public static final int SUB = 7;
  public static final int MUL = 8;
  public static final int DIV = 9;
  public static final int AND = 10;
  public static final int OR = 11;
  public static final int NOT = 12;
  public static final int RET = 13;
  public static final int PRN = 14;
  public static final int HLT = 15;

  int[] code = new int[1024]; // 1k of code
  int pc; // program counter

  int[] stack = new int[1024]; // 1k of data
  int sp; // stack pointer
  int fp; // frame pointer

  void push(int n) {
    stack[sp++] = n;
  }

  int pop() {
    return stack[--sp];
  }

  //-------------------------------------------------------------------
  // instructions
  void pim(int n) {
    push(n);
    pc += 2;
  }

  void sto() {
    stack[fp + pop()] = pop();
    pc++;
  }

  void ldp() {
    push(stack[fp + pop()]);
    pc++;
  }

  void add() {
    push(pop() + pop());
    pc++;
  }

  void sub() {
    push(pop() - pop());
    pc++;
  }

  void mul() {
    push(pop() * pop());
    pc++;
  }

  void div() {
    push(pop() / pop());
    pc++;
  }

  void and() {
    push(pop() & pop());
    pc++;
  }

  void or() {
    push(pop() | pop());
    pc++;
  }

  void not() {
    push(pop() ^ 0xFFFFFFFF);
    pc++;
  }

  void jmp(int addr) {
    pc = addr;
  }

  void jiz(int addr) {
    if (pop() == 0) {
      pc = addr;
    } else {
      pc += 2;
    }
  }

  void cll(int addr) {
    push(fp);
    push(pc + 2);
    fp = sp - 2;
    pc = addr;
  }

  void ret() {
    sp = fp + 2;
    pc = pop();
    fp = pop();
  }

  void prn() {
    System.out.println(pop());
    pc++;
  }

  void hlt() {
    pc = -1;
  }

  void printStack() {
    System.out.print("[");
    for (int i = 0; i < sp; i++) {
      System.out.printf("%X ", stack[i]);
    }
    System.out.println("]");
//    System.out.printf("] fp:%d sp:%d pc:%X inst:%X %n", fp, sp, pc, code[pc]);
  }

  void run(String filename, boolean trace) throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(filename));
    int i = 0;
    for (String line = br.readLine(); line != null; line = br.readLine()) {
      code[i++] = Integer.parseInt(line, 16);
    }

    pc = 0;
    fp = 0;
    sp = 0;
    while (pc != -1) {
      int inst = code[pc];
      int data = inst <= 2 ? code[pc + 1] : 0;

      //System.out.println(inst);
      switch (inst) {
        case PIM:
          pim(data);
          break;
        case JIZ:
          jiz(data);
          break;
        case CLL:
          cll(data);
          break;
        case POP:
          pop();
          pc++;
          break;
        case STO:
          sto();
          break;
        case LDP:
          ldp();
          break;
        case ADD:
          add();
          break;
        case SUB:
          sub();
          break;
        case MUL:
          mul();
          break;
        case DIV:
          div();
          break;
        case AND:
          and();
          break;
        case OR:
          or();
          break;
        case NOT:
          not();
          break;
        case RET:
          ret();
          break;
        case PRN:
          prn();
          break;
        case HLT:
          hlt();
      }
      if (trace) {
        printStack();
      }
    }
  }

  public static void main(String[] args) throws Exception {
    new Prism().run(args[0], args.length == 2);
  }
}
