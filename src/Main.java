//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        FileSystem fs = new FileSystem();

        fs.mkdir("/a/b/c");
        fs.mkdir("/a/b/d");
        fs.cd("/a/b/.*"); // matches first: c
        System.out.println(fs.pwd()); // /a/b/c

        fs.cd(".."); // up to b
        System.out.println(fs.pwd()); // /a/b

        fs.cd("d");
        System.out.println(fs.pwd()); // /a/b/d

        fs.cd("/"); // go to root
        System.out.println(fs.pwd()); // /
    }
}