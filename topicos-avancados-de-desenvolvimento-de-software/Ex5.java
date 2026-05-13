//O banco Firmeza desenvolveu um sistema de contas e saldos bancários e lhe contratou para executar um teste de performance
// para avaliar a robusteza e a quantidade de transações possíveis de serem processadas por 3 minutos.

// Para avaliar a performance do sistema foram criadas 100 contas correntes fictícias com R$ 1.000,00 de saldo inicial.
// Crie então um teste com programação concorrente que simule 5 sistemas fazendo transferências de valores aleatórios de até R$ 2.500,00
// de uma conta aleatória para outra igualmente aleatória. A cada 5 segundos, imprima o valor total no cofre do banco, que deverá ser sempre
// de R$ 100.000,00. Após 3 minutos de execução,
// verifique a quantidade de transações que foram executadas e se o valor total no cofre continua sendo R$ 100.000,00.

import java.util.*;

public class Ex5 {

//    atributpos
    static class Conta {
        int id;
        double saldo;

        Conta(int id, double saldo) {
            this.id = id;
            this.saldo = saldo;
        }
    }

//    listas
    static ArrayList<Conta> contas = new ArrayList<>();
    static Random rand = new Random();
    static int cont = 0;

//psvm
    public static void main(String[] args) {

        // cria as contas
        for (int i = 0; i < 100; i++) {
            contas.add(new Conta(i, 1000));
        }

        // threads fazendo transferencia
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        int i1 = rand.nextInt(100);
                        int i2 = rand.nextInt(100);
                        if (i1 == i2) continue;
                        double v = rand.nextDouble() * 2500;
                        transferir(contas.get(i1), contas.get(i2), v);
                        synchronized (Ex5.class) {
                            cont++;
                        }
                    }
                }
            }).start();
        }

        // thread pra mostrar total
        new Thread(new Runnable(){
            public void run() {
                while (true) {
                    try {
//       time de 5s
                        Thread.sleep(5000);
                    } catch (Exception e) {}
                    System.out.println("Total no banco: " + soma());
                }
            }
        }).start();

        // tempo de exec
        try {
            Thread.sleep(180000);
        } catch (Exception e) {}
        System.out.println("acabou");
        System.out.println("transacoes: " + cont);
        System.out.println("total final: " + soma());
        System.exit(0);
    }

//    obj das 2 contas
    static void transferir(Conta c1, Conta c2, double v){
        Conta p = c1.id < c2.id ? c1 : c2;
        Conta s = c1.id < c2.id ? c2 : c1;

        synchronized (p) {
            synchronized (s){
                if (c1.saldo >= v) {
                    c1.saldo -= v;
                    c2.saldo += v;
                }
            }
        }
    }

//      soma
    static double soma(){
        double total = 0;
        for (Conta c : contas) {
            synchronized (c) {
                total += c.saldo;
            }
        }
        return total;
    }
}