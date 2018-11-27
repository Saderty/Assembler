package Assembler;

import static Assembler.Assembler.counter;
import static Assembler.Assembler.counterStack;
import static Assembler.Memory.*;

class Operations {
    static void toGoto(String s) {
        for (String aGoto : Assembler.labels) {
            if (aGoto != null) {
                if (s.equals(aGoto.split(" ")[0])) {
                    Assembler.counter = Integer.parseInt(aGoto.split(" ")[1]) + 1;
                }
            }
        }
    }

    static void runOperations(String program) {
        String[] arguments = program.split(" ");

        switch (arguments[0]) {
            case "CALL":
                counterStack = counter;
                toGoto(arguments[1]);
                break;

            case "RET":
                counter = Assembler.counterStack + 1;
                break;

            case "PUSH":
                pushStack(getRegisterPair(arguments[1]));
       //         counter++;
                break;

            case "POP":
                popStack(getRegisterPair(arguments[1]));
       //         counter++;
                break;

            case "XCHG":
                String de = regDE.getValue();
                String hl = regHL.getValue();
                regHL.setValue(de);
                regDE.setValue(hl);
        //        counter++;
                break;

            case "CMA":
                regA.setValue(String.valueOf(~Integer.parseInt(regA.getValue())));
        //        counter++;
                break;

            case "INR":
                incRegister(getRegister(arguments[1]));
       //         counter++;
                break;

            case "RRC":
                cycleShift(true);
         //       counter++;
                break;

            case "RAR":
                cycleShift(true);
        //        counter++;
                break;

            case "RAL":
                cycleShift(false);
       //         counter++;
                break;

            case "ANA":
                andShift(arguments[1]);
       //         counter++;
                break;

            case "ANI":
                andShift(arguments[1]);
      //          counter++;
                break;

            case "ADI":
                addRegister(regA, arguments[1]);
        //        counter++;
                break;

            case "ORA":
                orShift(arguments[1]);
       //         counter++;
                break;

            case "XRA":
                xraShift(arguments[1]);
        //        counter++;
                break;

            case "MVI":
                getRegister(arguments[1]).setValue(arguments[2]);
       //         counter++;
                break;

            case "ADD":
                addRegister(regA, getRegister(arguments[1]));
         //       counter++;
                break;

            case "ADC":
                if (!flagC) {
                    addRegister(regA, getRegister(arguments[1]));
                } else {
                    addRegister(regA, getRegister(arguments[1]));
                    incRegister(regA);
                    flagC = false;
                }
        //        counter++;
                break;

            case "CPI":
                int a2 = Integer.parseInt(regA.getValue(), 16);
                int b2 = Integer.parseInt(arguments[1], 16);
                flagC = a2 < b2;
          //      counter++;
                break;

            case "CMC":
                flagC = !flagC;
          //      counter++;
                break;

            case "SUB":
                subRegister(regA, getRegister(arguments[1]));
           //     counter++;
                break;

            case "DCR":
                if (getRegister(arguments[1]).getValue().equals("00")) {
                    flagZ = true;
                } else {
                    decRegister(getRegister(arguments[1]));
                }
            //    counter++;
                break;

            case "MOV"://Reg -> Reg
                getRegister(arguments[1]).setValue(getRegister(arguments[2]).getValue());
          //      counter++;
                break;

            case "LDA"://d16 -> a16 -> RegA
                regA.setValue(addresses[Integer.parseInt(arguments[1])]);
          //      counter++;
                break;

            case "LDAX"://RP -> a16 -> RegA
                regA.setValue(getRegisterPairAddressValue(getRegisterPair(arguments[1])));
           //     counter++;
                break;

            case "LXI"://d16 -> RP
              /*  for (Assembler.Registers.Register[] aRegPair : regPair) {
                    if (getRegister(arguments[1]) == aRegPair[0]) {
                        aRegPair[0].setValue(arguments[2].substring(0, 2));
                        aRegPair[1].setValue(arguments[2].substring(2, 4));
                    }
                }*/
                getRegisterPair(arguments[1]).setValue(arguments[2]);
          //      counter++;
                break;

            case "INX":
                incRegisterPair(getRegisterPair(arguments[1]));
          //      counter++;
                break;

            case "JMP":
                toGoto(arguments[1]);
                break;

            case "JC":
                if (flagC) {
                    toGoto(arguments[1]);
                    flagC = false;
                } else {
            //        counter++;
                }
                break;

            case "JNC":
                if (!flagC) {
                    toGoto(arguments[1]);
                } else {
                    flagC = false;
            //        counter++;
                }
                break;

            case "JZ":
                if (flagZ) {
                    toGoto(arguments[1]);
                    flagZ = false;
                } else {
             //       counter++;
                }
                break;

            case "JNZ":
                if (!flagZ) {
                    toGoto(arguments[1]);
                } else {
                    flagZ = false;
             //       counter++;
                }
                break;

            case "JPE":
                if (flagP) {
                    toGoto(arguments[1]);
                    flagP = false;
                } else {
             //       counter++;
                }
                break;

            case "JPO":
                if (!flagP) {
                    toGoto(arguments[1]);
                } else {
           //         counter++;
                }
                break;

            case "STAX"://RegA -> RP -> a16
                setAddress(getRegisterPair(arguments[1]), regA);
                regA.setValue("00");
            //    counter++;
                break;

            case "SET":
                setAddress(Integer.parseInt(arguments[1]), arguments[2]);
              //  counter++;
                break;

            case "GET":
                if (arguments[1].length() == 1) {
                    System.out.println("Register " + arguments[1] + " = " + getRegister(arguments[1]).getValue());
                }
                if (arguments[1].length() == 4) {
                    System.out.println("Address " + arguments[1] + " = " + addresses[Integer.parseInt(arguments[1])]);
                }
               // counter++;
                break;

            default:
                break;
        }
        counter++;

        if (arguments[0].contains(":")) {
            counter++;
        }
    }
}
