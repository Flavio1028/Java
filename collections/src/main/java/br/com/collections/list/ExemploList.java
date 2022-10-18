package br.com.collections.list;

import java.util.*;

/**
 * Crie uma Lista e execute as seguintes operações:
 *      Adicione 5 nomes: Juliana, Pedro, Carlos, Larissa e João
 *      Navegue na lista exibindo cada nome no console
 *      Substitua o nome Carlos por Roberto
 *      Retorne o nome da posição 1
 *      Remova o nome da posição 4, remova o nome João
 *      Retorne a quantidade de itens da lista
 *      Verifique se o nome Juliana existe na lista
 * Crie uma nova lista com os nomes: Rodrigo, Ismael
 *      Ordene os itens da lista por ordem alfabética
 *      Verifique se a lista esta vazia
 */
public class ExemploList {

    public static void main(String[] args) {

        // Criando a lista
        List<String> names = new ArrayList(Arrays.asList("Juliana", "Pedro", "Carlos", "Larissa", "João"));

        // Exibindo itens da lista
        System.out.println("Exibindo itens da lista: ");
        for(String name: names){
            System.out.println(String.format("** %s **", name));
        }

        // Alterando valor Carlos por Roberto
        names.set(2, "Roberto");
        System.out.println("Lista apos alteração: " + names);

        // Retornando item da posicao 1
        System.out.println("---> " + names.get(1));

        // Removento item da posicao 4
        names.remove(4);
        System.out.println("Lista apos remocao do item 4: " + names);

        // Total de itens da lista
        System.out.println("Total de itens da lista:" + names.size());

        // Verificando se Juliana esta na lista
        boolean hasJuliana = names.contains("Juliana");
        System.out.println(String.format("Juliana %s na lista", (hasJuliana ? "está": "não está")));

        // Criando a lista
        List<String> listOrder = new ArrayList(Arrays.asList("Rodrigo", "Ismael"));
        System.out.println("Lista não ordenada: " + listOrder);
        listOrder.sort(Comparator.naturalOrder());
        System.out.println("Lista ordenada: " + listOrder);

    }

}
