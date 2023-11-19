package uos.teamkernel.sim;

import uos.teamkernel.view.InitDialogForm;

public class App {
    public static void main(String[] args) {
        // Ask for input to initialize
        new InitDialogForm(Thread.currentThread());
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            System.out.println("Initialize done. Start Simulator.");
        }
    }
}
