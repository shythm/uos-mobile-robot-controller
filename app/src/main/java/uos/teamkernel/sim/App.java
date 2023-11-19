package uos.teamkernel.sim;

import uos.teamkernel.view.InitForm;

public class App {
    public static void main(String[] args) {
        InitForm initForm = new InitForm();
        initForm.addParentsThread(Thread.currentThread());

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            System.out.println("Initialize done. Start Simulater.");
        }

    }
}
