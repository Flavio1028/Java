package br.com.collections.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Crie uma Lista e execute as seguintes operações:
 *      Adicione 5 nomes: Juliana, Pedro, Carlos, Larissa e João
 *      Navegue na lista exibindo cada nome no console
 *      Retorne o primeiro item da lista, sem removê-lo
 *      Retorne o primeiro item da lista, removendo o mesmo
 *      Adicione um novo nome: Daniel. Verifique a posição que o mesmo entrou na fila
 *      Retorne o tamanho da lista
 *      Verifique se a lista esta vazia
 *      Verifique se o nome carlos esta na lista
 */
public class ExemploLinkedList {

    public static void main(String[] args) {

        // Cria a lista e adiciona os itens nela
        Queue<String> nameList = new LinkedList<>();

        nameList.add("Juliana");
        nameList.add("Pedro");
        nameList.add("Carlos");
        nameList.add("Larissa");
        nameList.add("João");

        // Exibe os dados da lista
        System.out.println("Lista inicializada -> " + nameList + "\n");

        // Retorna o primeiro nome da lista sem removê-lo
        String fristName = nameList.peek();
        System.out.println("Primeiro nome: " + fristName);
        System.out.println("Itens da Lista -> " + nameList);

        // Retorna o primeiro nome da lista removendo
        fristName = nameList.poll();
        System.out.println("Primeiro nome: " + fristName);
        System.out.println("Itens da Lista -> " + nameList);

        // Adiciona um novo item a lista
        nameList.add("Daniel");
        System.out.println("Foi adicionado Daniel na lista na posição " + (nameList.size() - 1) );
        System.out.println("Itens da Lista -> " + nameList);

        // Tamanho da lista
        System.out.println("Tamanho da lista: " + nameList.size() );

        // Verifica se a lista esta vazia
        System.out.println("A lista esta vazia ? " + nameList.isEmpty());

        // Verifica se carlos esta na lista
        System.out.println("Carlos esta na lista " + nameList.contains("Carlos"));

    }

}
