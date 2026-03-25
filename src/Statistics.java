import java.util.concurrent.atomic.AtomicInteger;

public class Statistics implements Runnable {
    private AtomicInteger clientsCount = new AtomicInteger(0), totalClientsCount = new AtomicInteger(0);
    private AtomicInteger ADDCount = new AtomicInteger(0), totalADDCount = new AtomicInteger(0);
    private AtomicInteger SUBCount = new AtomicInteger(0), totalSUBCount = new AtomicInteger(0);
    private AtomicInteger MULCount = new AtomicInteger(0), totalMULCount = new AtomicInteger(0);
    private AtomicInteger DIVCount = new AtomicInteger(0), totalDIVCount = new AtomicInteger(0);
    private AtomicInteger ERRORCount = new AtomicInteger(0), totalERRORCount = new AtomicInteger(0);
    private AtomicInteger operCount = new AtomicInteger(0), totalOperCount = new AtomicInteger(0);
    private AtomicInteger result = new AtomicInteger(0), totalResult = new AtomicInteger(0);

    //    Updates counting objects.
    public void reportOperation(String oper, int result, boolean error) {
        if (error) {
            ERRORCount.incrementAndGet();
        } else {
            operCount.incrementAndGet();

            switch (oper) {
                case "ADD":
                    ADDCount.incrementAndGet();
                    break;
                case "SUB":
                    SUBCount.incrementAndGet();
                    break;
                case "MUL":
                    MULCount.incrementAndGet();
                    break;
                case "DIV":
                    DIVCount.incrementAndGet();
                    break;
            }
        }

        this.result.addAndGet(result);
    }

    //    Updates clientCount.
    public void clientCounter() {
        clientsCount.incrementAndGet();
    }

    //    Outputs and updates counting objects (data).
    @Override
    public void run() {
        totalClientsCount.addAndGet(clientsCount.get());
        totalOperCount.addAndGet(operCount.get());
        totalADDCount.addAndGet(ADDCount.get());
        totalSUBCount.addAndGet(SUBCount.get());
        totalMULCount.addAndGet(MULCount.get());
        totalDIVCount.addAndGet(DIVCount.get());
        totalERRORCount.addAndGet(ERRORCount.get());
        totalResult.addAndGet(result.get());

        System.out.println("\n----------------------------------------------");
        System.out.println("NEW");
        System.out.printf("Clients: %d, Operations: %d, ADD: %d, SUB: %d, MUL: %d, DIV: %d, ERROR: %d, SUM: %d",
                clientsCount.get(), operCount.get(),
                ADDCount.get(), SUBCount.get(), MULCount.get(), DIVCount.get(),
                ERRORCount.get(), result.get());
        System.out.println();

        System.out.println("TOTAL");
        System.out.printf("Clients: %d, Operations: %d, ADD: %d, SUB: %d, MUL: %d, DIV: %d, ERROR: %d, SUM: %d",
                totalClientsCount.get(), totalOperCount.get(),
                totalADDCount.get(), totalSUBCount.get(), totalMULCount.get(), totalDIVCount.get(),
                totalERRORCount.get(), totalResult.get());
        System.out.println();
        System.out.println("----------------------------------------------\n");

        clientsCount.set(0);
        operCount.set(0);
        ADDCount.set(0);
        SUBCount.set(0);
        MULCount.set(0);
        DIVCount.set(0);
        ERRORCount.set(0);
        result.set(0);
    }
}
