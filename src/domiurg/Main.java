package domiurg;

public class Main {
    public static void main(String[] args) throws Exception{
        SQLConnect db = new SQLConnect();

        while (true){
            System.out.println(db.readDB());

            try {
                Thread.sleep(2000);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

    }
}
