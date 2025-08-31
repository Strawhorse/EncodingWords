package ie.atu.sw;

public class Menu {

    public void menu() {

//        Removed the colouring scheme as it was causing some issues when I was trying to run the program

        System.out.println("************************************************************");
        System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
        System.out.println("*                                                          *");
        System.out.println("*               Student number: G00472878                  *");
        System.out.println("*                                                          *");
        System.out.println("*              Encoding Words with Suffixes                *");
        System.out.println("*                                                          *");
        System.out.println("************************************************************");
        System.out.println("(0) Instructions to use program");
        System.out.println("(1) Program setup status");
        System.out.println("(2) Specify Mapping File");
        System.out.println("(3) Specify Text File location to Encode");
        System.out.println("(4) Encode Text File");
        System.out.println("(5) Specify Text File location to Decode");
        System.out.println("(6) Decode Text File");
        System.out.println("(7) Show menu options again");
        System.out.println("(?) Quit");

        System.out.print("Select Option [1-?]> ");
    }
}
