package ie.atu.sw;

public class Menu {

    public void menu() {
        //You should put the following code into a menu or Menu class
        System.out.println(ConsoleColour.WHITE_BOLD_BRIGHT);
        System.out.println("************************************************************");
        System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
        System.out.println("*                                                          *");
        System.out.println("*               Student number: G00472878                  *");
        System.out.println("*                                                          *");
        System.out.println("*              Encoding Words with Suffixes                *");
        System.out.println("*                                                          *");
        System.out.println("************************************************************");
        System.out.println("(1) Specify Mapping File");
        System.out.println("(2) Specify Text File to Encode");
        System.out.println("(3) Specify Output File (default: ./out.txt)");
        System.out.println("(4) Configure Options");
        System.out.println("(5) Encode Text File");
        System.out.println("(6) Decode Text File");
        System.out.println("(7) Show menu options again");
        System.out.println("(?) Optional Extras...");
        System.out.println("(?) Quit");

        //Output a menu of options and solicit text from the user
        System.out.print(ConsoleColour.WHITE_BOLD_BRIGHT);
        System.out.print("Select Option [1-?]>");
        System.out.println();
    }
}
